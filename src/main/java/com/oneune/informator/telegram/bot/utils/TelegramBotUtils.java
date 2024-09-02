package com.oneune.informator.telegram.bot.utils;

import com.oneune.informator.rest.store.exceptions.BusinessLogicException;
import jakarta.annotation.Nullable;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.internal.Pair;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
@Log4j2
public class TelegramBotUtils {

    private final static String UPDATE_CHILD_ID_NAME = "technical";

    public Long extractChatId(Update update) {
        Long chatId;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new BusinessLogicException("Not predicted state!");
        }
        return chatId;
    }

    public  <S extends Serializable,
             M extends BotApiMethod<S>> S uncheckedExecute(DefaultAbsSender bot,
                                                           M method) {
        try {
            return bot.execute(method);
        } catch (TelegramApiException e) {
            String message = "Сообщение не отправлено!";
            log.error("%s - %s".formatted(message, e.getMessage()), e);
            throw new BusinessLogicException(message, e);
//            return null;
        }
    }

    public Message informAboutDeveloping(DefaultAbsSender bot, Update update) {
        Long chatId = TelegramBotUtils.extractChatId(update);
        SendMessage informAboutDevelopingMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text("""
                        Пока что в разработке...
                        Но уже скоро этот функционал появится!
                        """)
                .build();
        return TelegramBotUtils.uncheckedExecute(bot, informAboutDevelopingMessage);
    }

    public Message informAboutSuccess(DefaultAbsSender bot, Update update) {
        Long chatId = TelegramBotUtils.extractChatId(update);
        SendMessage informAboutDevelopingMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text("""
                        Все успешно обработано и сохранено!
                        """)
                .build();
        return TelegramBotUtils.uncheckedExecute(bot, informAboutDevelopingMessage);
    }

    public Message informAboutError(DefaultAbsSender bot, Update update, String message) {
        Long chatId = TelegramBotUtils.extractChatId(update);
        SendMessage informAboutDevelopingMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(message)
                .build();
        return TelegramBotUtils.uncheckedExecute(bot, informAboutDevelopingMessage);
    }

    public static Message handleUnknownUpdateType(Update update, DefaultAbsSender bot) {
        log.warn("Unknown update type!");
        SendMessage unknownUpdateTypeMessage = SendMessage.builder()
                .chatId(extractChatId(update).toString())
                .text("Я тебя не понял...\nВыбери знакомые мне команды\n")
                .build();
        return uncheckedExecute(bot, unknownUpdateTypeMessage);
    }

    public static Boolean alert(DefaultAbsSender bot, CallbackQuery callbackQuery, String message) {
        AnswerCallbackQuery telegramAlert = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .text(message)
                .showAlert(true)
                .build();
        return uncheckedExecute(bot, telegramAlert);
    }

    /**
     * If chat ID is null or message ID is null, then nothing do.
     */
    public <CHAT_ID extends Long,
            MESSAGE_ID extends Integer> Boolean deleteMessages(DefaultAbsSender bot,
                                                               Map<CHAT_ID, List<MESSAGE_ID>> ids) {
        return ids.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(messageId -> Pair.of(entry.getKey(), messageId)))
                .filter(pair -> Objects.nonNull(pair.getLeft()) && Objects.nonNull(pair.getRight()))
                .map(pair -> DeleteMessage.builder().chatId(pair.getLeft().toString()).messageId(pair.getRight()).build())
                .allMatch(deleteMessage -> uncheckedExecute(bot, deleteMessage));
    }

    /**
     * If chat ID is null or message ID is null, then nothing do.
     */
    public <CHAT_ID extends Long,
            MESSAGE_ID extends Integer> Boolean deleteMessage(DefaultAbsSender bot,
                                                              @Nullable CHAT_ID chatId,
                                                              @Nullable MESSAGE_ID messageId) {
        return deleteMessages(bot, Map.of(chatId, List.of(messageId)));
    }
}
