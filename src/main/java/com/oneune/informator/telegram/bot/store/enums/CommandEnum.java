package com.oneune.informator.telegram.bot.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum CommandEnum {

    START(UpdateTypeEnum.COMMAND.getPrefix() + "start", "Начать выбирать машину"),
    TRACK(UpdateTypeEnum.COMMAND.getPrefix() + "track", "Отследить по трек-номеру"),
    HISTORY_TRACK(UpdateTypeEnum.COMMAND.getPrefix() + "history_track", "История отслеживаний по трек-номеру");

    String value;
    String description;
}
