package com.oneune.informator.telegram.bot.abstracts;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Раскидывает обновления по классам-обработчикам.
 */
@FunctionalInterface
public interface Dispatcher {
    void distribute(DefaultAbsSender bot, Update update);
}
