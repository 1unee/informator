package com.oneune.informator.rest.services;

import com.oneune.informator.rest.store.dtos.russian_mail.HistoryRecordDto;
import com.oneune.informator.rest.store.dtos.russian_mail.OperationHistoryDto;
import com.oneune.informator.telegram.bot.configs.properies.TelegramBotProperties;
import com.oneune.informator.telegram.bot.utils.TelegramBotUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ButtonBuilderService {

    TelegramBotProperties telegramBotProperties;

    public SendMessage buildStartCommandMessage(Update update) {
        return SendMessage.builder()
                .chatId(TelegramBotUtils.extractChatId(update).toString())
                .text("""
                      Привет, %s! Я твой помощник.
                      Сейчас я умею помогать в поиске информации об отправлении почтой России.
                      
                      Слева снизу есть кнопка меню, нажав на нее, высветится меню из кнопок «%s».
                      
                      Выбери походящую опцию.
                      """.formatted(
                              update.getMessage().getFrom().getUserName(),
                              String.join(", ", telegramBotProperties.getMenu().getCommand()
                                      .values()
                                      .stream()
                                      .map(TelegramBotProperties.TelegramBotCommandProperties::getDescription)
                                      .toList())
                ))
                .build();
    }

    public SendMessage build(Update update) {

        InlineKeyboardButton webAppStartPageButton = InlineKeyboardButton.builder()
                .text("Начать с короткого знакомства")
                .callbackData("EMPTY")
                .build();

        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(webAppStartPageButton)
        );

        return SendMessage.builder()
                .chatId(TelegramBotUtils.extractChatId(update).toString())
                .text("Text")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(keyboard)
                        .build())
                .build();
    }

    public SendMessage buildTrackMessage(Update update) {
        return SendMessage.builder()
                .chatId(TelegramBotUtils.extractChatId(update).toString())
                .text("""
                      Отправь мне свой трек-номер следующим сообщением.
                      """)
                .replyMarkup(InlineKeyboardMarkup.builder().build())
                .build();
    }

    private String getPrettyTimestamp(String stringifiedTimestamp) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(stringifiedTimestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        return zonedDateTime.format(formatter);
    }

    public SendMessage buildOperationHistory(Update update, OperationHistoryDto history) {

        List<HistoryRecordDto> records = history.getRecords().stream()
                .sorted(comparing(record -> OffsetDateTime.parse(record.getOperationParameters().getStringifiedTimestamp()), reverseOrder()))
                .toList();

        String generalMessage = IntStream.rangeClosed(1, history.getRecords().size())
                .boxed()
                .map(index -> Pair.of(index, history.getRecords().get(index - 1)))
                .map(pair -> "%s. %s - %s (%s) (%s)".formatted(
                        pair.getLeft(),
                        pair.getRight().getAddressParameters().getDeparture().getDescription(),
                        pair.getRight().getOperationParameters().getAttributes().getName(),
                        pair.getRight().getOperationParameters().getType().getName(),
                        getPrettyTimestamp(pair.getRight().getOperationParameters().getStringifiedTimestamp())
                ))
                .collect(Collectors.joining("\n\n"));

        return SendMessage.builder()
                .chatId(TelegramBotUtils.extractChatId(update).toString())
                .text("""
                      Статус: <b>%s (%s)</b>
                      
                      %s
                      """.formatted(
                              records.isEmpty() ? "Ошибка" : records.getFirst().getOperationParameters().getType().getName(),
                              records.isEmpty() ? "Ошибка" : records.getFirst().getOperationParameters().getAttributes().getName().toLowerCase(),
                              generalMessage
                ))
                .parseMode("HTML")
                .build();
    }
}
