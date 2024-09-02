package com.oneune.informator.telegram.bot.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum UpdateTypeEnum {

    COMMAND("/", "Команда"),
    NUDE_TEXT("", "Текст"),
    CALLBACK_QUERY("/cq_", "Коллбэк"),
    UNKNOWN("", "Неизвестно");

    String prefix;
    String description;
}
