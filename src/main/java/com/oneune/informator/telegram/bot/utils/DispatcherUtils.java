package com.oneune.informator.telegram.bot.utils;

import com.oneune.informator.telegram.bot.store.enums.UpdateTypeEnum;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@UtilityClass
public final class DispatcherUtils {

    public UpdateTypeEnum classifyUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.getText().startsWith(UpdateTypeEnum.COMMAND.getPrefix())) {
                return UpdateTypeEnum.COMMAND;
            } else if (message.hasText() && message.getText().startsWith(UpdateTypeEnum.NUDE_TEXT.getPrefix())) {
                return UpdateTypeEnum.NUDE_TEXT;
            } else {
                return UpdateTypeEnum.UNKNOWN;
            }
        } else if (update.hasCallbackQuery()
                && update.getCallbackQuery().getData().startsWith(UpdateTypeEnum.CALLBACK_QUERY.getPrefix())) {
            return UpdateTypeEnum.CALLBACK_QUERY;
        } else {
            return UpdateTypeEnum.UNKNOWN;
        }
    }
}
