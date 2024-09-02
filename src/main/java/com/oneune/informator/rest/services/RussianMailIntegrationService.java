package com.oneune.informator.rest.services;

import com.oneune.informator.rest.configs.properties.RussianMailIntegrationProperties;
import com.oneune.informator.rest.repositories.ActionRepository;
import com.oneune.informator.rest.repositories.RequestHistoryRepository;
import com.oneune.informator.rest.repositories.UpdateRepository;
import com.oneune.informator.rest.repositories.UserRepository;
import com.oneune.informator.rest.store.dtos.russian_mail.OperationHistoryDto;
import com.oneune.informator.rest.store.entities.ActionEntity;
import com.oneune.informator.rest.store.entities.RequestHistoryEntity;
import com.oneune.informator.rest.store.entities.UpdateEntity;
import com.oneune.informator.rest.store.entities.UserEntity;
import com.oneune.informator.telegram.bot.abstracts.Command;
import com.oneune.informator.telegram.bot.store.enums.CommandEnum;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class RussianMailIntegrationService {

    RussianMailIntegrationProperties russianMailIntegrationProperties;
    UserService userService;
    ActionRepository actionRepository;
    UserRepository userRepository;
    UpdateRepository updateRepository;
    RequestHistoryRepository requestHistoryRepository;

    @SneakyThrows
    private SOAPMessage getResponse(String orderBarcode) {
        // Cоздаем соединение
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
        String url = "https://tracking.russianpost.ru/rtm34";

        // Cоздаем сообщение
        MessageFactory messageFactory = MessageFactory.newInstance("SOAP 1.2 Protocol");
        SOAPMessage message = messageFactory.createMessage();

        // Создаем объекты, представляющие различные компоненты сообщения
        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();
        envelope.addNamespaceDeclaration("soap","http://www.w3.org/2003/05/soap-envelope");
        envelope.addNamespaceDeclaration("oper","http://russianpost.org/operationhistory");
        envelope.addNamespaceDeclaration("data","http://russianpost.org/operationhistory/data");
        envelope.addNamespaceDeclaration("soapenv","http://schemas.xmlsoap.org/soap/envelope/");
        SOAPElement operElement = body.addChildElement("getOperationHistory", "oper");
        SOAPElement dataElement = operElement.addChildElement("OperationHistoryRequest","data");
        SOAPElement barcode = dataElement.addChildElement("Barcode", "data");
        SOAPElement messageType = dataElement.addChildElement("MessageType","data");
        SOAPElement language = dataElement.addChildElement("Language","data");
        SOAPElement dataAuth = operElement.addChildElement("AuthorizationHeader","data");
        SOAPFactory sf = SOAPFactory.newInstance();
        Name must = sf.createName("mustUnderstand","soapenv","http://schemas.xmlsoap.org/soap/envelope/");
        dataAuth.addAttribute(must,"1");
        SOAPElement login = dataAuth.addChildElement("login", "data");
        SOAPElement password = dataAuth.addChildElement("password","data");

        // Заполняем значения
        barcode.addTextNode(orderBarcode);
        messageType.addTextNode("0"); // 0 or 1
        language.addTextNode("RUS");
        login.addTextNode(russianMailIntegrationProperties.getAuthentication().getLogin());
        password.addTextNode(russianMailIntegrationProperties.getAuthentication().getPassword());

        // Сохранение сообщения
        message.saveChanges();
        SOAPMessage msg = connection.call(message, url);
        connection.close();
        return msg;
    }

    @SneakyThrows
    public OperationHistoryDto integrate(String orderBarcode) {

        SOAPMessage soapmsg = getResponse(orderBarcode);

        Node responseNode = soapmsg.getSOAPBody().getElementsByTagNameNS(
                "http://russianpost.org/operationhistory/data",
                "OperationHistoryData"
        ).item(0);

        // Если элемент найден, выполняем десериализацию
        if (responseNode != null) {
            JAXBContext jaxbContext = JAXBContext.newInstance(OperationHistoryDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Анмаршалим элемент в объект Java
            Source source = new DOMSource(responseNode);
            logXml(source);

            return (OperationHistoryDto) unmarshaller.unmarshal(source);
        } else {
            throw new JAXBException("Element getOperationHistoryResponse not found in SOAP message.");
        }
    }

    @Transactional
    public OperationHistoryDto getOperationHistoryByParcelBarcode(User telegramUser,
                                                                  String barcode,
                                                                  boolean controlLimit) {
        UserEntity userEntity = userRepository.findByTelegramId(telegramUser.getId()).orElseThrow();
        actionRepository.save(ActionEntity.builder()
                .user(userEntity)
                .timestamp(LocalDateTime.now())
                .description("Action")
                .build());
        if (controlLimit) {
            if (userService.isActionAvailable(telegramUser)) {
                OperationHistoryDto operationHistoryDto = integrate(barcode);
                createUserRequestHistory(userEntity, barcode);
                return operationHistoryDto;
            } else {
                throw new RuntimeException("New action is not available!");
            }
        } else {
            OperationHistoryDto operationHistoryDto = integrate(barcode);
            createUserRequestHistory(userEntity, barcode);
            return operationHistoryDto;
        }
    }

    @SneakyThrows
    private void logXml(Source source) {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new FileWriter("out_example.xml"));
        t.transform(source, result);
    }

    @Transactional
    protected void createUserRequestHistory(UserEntity user, String barcode) {
        RequestHistoryEntity requestHistory = requestHistoryRepository.findByBarcode(barcode)
                .orElseGet(() -> RequestHistoryEntity.builder()
                        .user(user)
                        .barcode(barcode)
                        .timestamp(LocalDateTime.now())
                        .build());
        requestHistoryRepository.saveAndFlush(requestHistory);
    }
}