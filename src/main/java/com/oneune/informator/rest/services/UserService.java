package com.oneune.informator.rest.services;

import com.oneune.informator.rest.configs.properties.RussianMailIntegrationProperties;
import com.oneune.informator.rest.readers.UserReader;
import com.oneune.informator.rest.repositories.ActionRepository;
import com.oneune.informator.rest.repositories.UpdateRepository;
import com.oneune.informator.rest.repositories.UserRepository;
import com.oneune.informator.rest.store.dtos.UserDto;
import com.oneune.informator.rest.store.entities.ActionEntity;
import com.oneune.informator.rest.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserService {

    ModelMapper modelMapper;
    UserRepository userRepository;
    UserReader userReader;
    RussianMailIntegrationProperties russianMailIntegrationProperties;
    ActionRepository actionRepository;
    UpdateRepository updateRepository;

    @Transactional
    public UserEntity register(User telegramUser) {
        return userRepository.saveAndFlush(UserEntity.builder()
                .username(telegramUser.getUserName())
                .telegramId(telegramUser.getId())
                .registeredAt(Instant.now())
                .build());
    }

    @Transactional
    public UserDto registerOrGet(User telegramUser) {
        // todo: кейс когда нет юзернейма
        String userName = Objects.isNull(telegramUser.getUserName()) ? "user_%s".formatted(telegramUser.getId()) : telegramUser.getUserName();
        telegramUser.setUserName(userName);
        Optional<UserDto> user = userReader.get(telegramUser.getUserName());
        return user.orElseGet(() -> modelMapper.map(register(telegramUser), UserDto.class));
    }

    @Transactional
    public UserDto edit(UserDto userDto) {
        UserEntity userEntity = this.userReader.getEntityById(userDto.getId());
        modelMapper.map(userDto, userEntity);
        userRepository.saveAndFlush(userEntity);
        return userReader.getById(userDto.getId());
    }

    @Transactional
    public boolean isActionAvailable(User telegramUser) {
        UserEntity user = userRepository.findByTelegramId(telegramUser.getId()).orElseGet(() -> register(telegramUser));
        List<ActionEntity> list = user.getActions().stream()
                .sorted(Comparator.comparing(ActionEntity::getTimestamp))
                .toList();

        List<ActionEntity> todayActions = actionRepository.findActionEntitiesByTimestampAfter(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))
        );

        return todayActions.size() < 100 || list.isEmpty() || LocalDateTime.now().isAfter(
                list.get(list.size() - 1).getTimestamp().plus(russianMailIntegrationProperties.getInterval())
        );
    }
}
