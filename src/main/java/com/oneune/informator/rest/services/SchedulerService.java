package com.oneune.informator.rest.services;

import com.oneune.informator.rest.repositories.RequestHistoryRepository;
import com.oneune.informator.rest.repositories.UpdateRepository;
import com.oneune.informator.telegram.bot.InformatorTelegramBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
//@ConditionalOnProperty(prefix = "scheduler", name = "check-parcel-delivering", value = "true")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class SchedulerService {

    ExecutorService executorService = Executors.newScheduledThreadPool(1);
    RequestHistoryRepository requestHistoryRepository;
    UpdateRepository updateRepository;
    InformatorTelegramBot bot;
    RussianMailIntegrationService russianMailIntegrationService;

//    public void doTask() {
//
//        // telegram user id : list chat ids
//        Map<Long, Set<Long>> longListMap = updateRepository.findAll().stream()
//                .map(UpdateEntity::getValue)
//                .filter(Objects::nonNull)
//                .filter(Update::hasMessage)
//                .map(Update::getMessage)
//                .collect(groupingBy(
//                        msg -> msg.getFrom().getId(),
//                        mapping(Message::getChatId, toSet())
//                ));
//
//        // todo: refactor findAll
//        Map<UserEntity, List<RequestHistoryEntity>> collect = requestHistoryRepository.findAll().stream()
//                .collect(groupingBy(RequestHistoryEntity::getUser));
//
//        for (Map.Entry<UserEntity, List<RequestHistoryEntity>> entry : collect.entrySet()) {
//            UserEntity userEntity = entry.getKey();
//            Set<Long> chatIdsByUser = longListMap.getOrDefault(userEntity.getTelegramId(), Set.of());
//            List<RequestHistoryEntity> requestHistoryEntities = entry.getValue();
//
//            for (RequestHistoryEntity requestHistoryEntity : requestHistoryEntities) {
//
//            }
//        }
//
//        executorService.submit(() -> )
//    }
}
