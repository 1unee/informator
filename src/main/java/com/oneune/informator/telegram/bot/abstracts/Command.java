package com.oneune.informator.telegram.bot.abstracts;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

@FunctionalInterface
public interface Command {
    void execute(DefaultAbsSender bot, Update update);
}
