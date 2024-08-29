package com.oneune.informator.telegram.bot.dispatchers;

import com.oneune.informator.rest.services.ButtonBuilderService;
import com.oneune.informator.rest.services.RussianMailIntegrationService;
import com.oneune.informator.rest.store.dtos.russian_mail.OperationHistoryDto;
import com.oneune.informator.telegram.bot.abstracts.Dispatcher;
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
public class NudeTextDispatcher implements Dispatcher {

    RussianMailIntegrationService russianMailIntegrationService;
    ButtonBuilderService buttonBuilderService;
    Map<Long, ChatStateEnum> chatStates;

    @Override
    public void distribute(DefaultAbsSender bot, Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText().trim();
            ChatStateEnum chatState = chatStates.get(TelegramBotUtils.extractChatId(update));

            if (chatState.equals(ChatStateEnum.WAIT_BARCODE) && text.chars().allMatch(Character::isDigit)) {
                OperationHistoryDto history = russianMailIntegrationService.getOperationHistoryByParcelBarcode(
                        update.getMessage().getFrom(), text, true
                );
                SendMessage historyMessage = buttonBuilderService.buildOperationHistory(update, history);
                chatStates.put(TelegramBotUtils.extractChatId(update), ChatStateEnum.DEFAULT);
                TelegramBotUtils.uncheckedExecute(bot, historyMessage);
            } else {
                TelegramBotUtils.handleUnknownUpdateType(update, bot);
            }

        } else {
            TelegramBotUtils.handleUnknownUpdateType(update, bot);
        }
    }
}
