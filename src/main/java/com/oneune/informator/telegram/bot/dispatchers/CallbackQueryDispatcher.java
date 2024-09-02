package com.oneune.informator.telegram.bot.dispatchers;

import com.oneune.informator.rest.services.ButtonBuilderService;
import com.oneune.informator.rest.services.RussianMailIntegrationService;
import com.oneune.informator.rest.store.dtos.russian_mail.OperationHistoryDto;
import com.oneune.informator.telegram.bot.abstracts.Dispatcher;
import com.oneune.informator.telegram.bot.store.enums.CommandEnum;
import com.oneune.informator.telegram.bot.utils.TelegramBotUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class CallbackQueryDispatcher implements Dispatcher {

    RussianMailIntegrationService russianMailIntegrationService;
    ButtonBuilderService buttonBuilderService;

    @Override
    public void distribute(DefaultAbsSender bot, Update update) {

        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackQueryData = callbackQuery.getData();

        if (callbackQueryData.contains(CommandEnum.HISTORY_TRACK.name())) {
            String barcode = callbackQueryData.split("_")[3];
            OperationHistoryDto history = russianMailIntegrationService.getOperationHistoryByParcelBarcode(
                    callbackQuery.getFrom(), barcode, true
            );
            SendMessage historyMessage = buttonBuilderService.buildOperationHistory(update, history);
            TelegramBotUtils.uncheckedExecute(bot, historyMessage);
        } else {
            TelegramBotUtils.handleUnknownUpdateType(update, bot);
        }
    }
}
