package com.oneune.informator.rest.services;

import com.oneune.informator.telegram.bot.abstracts.Command;
import com.oneune.informator.telegram.bot.store.enums.ChatStateEnum;
import com.oneune.informator.telegram.bot.utils.TelegramBotUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class BotService implements Command {

    UserService userService;
    ButtonBuilderService buttonBuilderService;
    Map<Long, ChatStateEnum> chatStates;

    @Override
    public void execute(DefaultAbsSender bot, Update update) {
        userService.registerOrGet(update.getMessage().getFrom());
        SendMessage startCommandMessage = buttonBuilderService.buildStartCommandMessage(update);
        chatStates.put(TelegramBotUtils.extractChatId(update), ChatStateEnum.DEFAULT);
        TelegramBotUtils.uncheckedExecute(bot, startCommandMessage);
    }

    public void sendTrackMessage(DefaultAbsSender bot, Update update) {
        SendMessage trackMessage = buttonBuilderService.buildTrackMessage(update);
        chatStates.put(TelegramBotUtils.extractChatId(update), ChatStateEnum.WAIT_BARCODE);
        TelegramBotUtils.uncheckedExecute(bot, trackMessage);
    }
}
