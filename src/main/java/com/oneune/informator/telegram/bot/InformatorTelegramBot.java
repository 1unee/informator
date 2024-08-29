package com.oneune.informator.telegram.bot;

import com.oneune.informator.rest.repositories.UpdateRepository;
import com.oneune.informator.rest.store.entities.UpdateEntity;
import com.oneune.informator.telegram.bot.abstracts.AbstractLongPollingTelegramBot;
import com.oneune.informator.telegram.bot.configs.properies.TelegramBotProperties;
import com.oneune.informator.telegram.bot.dispatchers.CommandDispatcher;
import com.oneune.informator.telegram.bot.dispatchers.NudeTextDispatcher;
import com.oneune.informator.telegram.bot.store.enums.UpdateTypeEnum;
import com.oneune.informator.telegram.bot.utils.DispatcherUtils;
import com.oneune.informator.telegram.bot.utils.TelegramBotUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class InformatorTelegramBot extends AbstractLongPollingTelegramBot {

    TelegramBotProperties telegramBotProperties;
    UpdateRepository updateRepository;
    CommandDispatcher commandDispatcher;
    NudeTextDispatcher nudeTextDispatcher;

    public InformatorTelegramBot(TelegramBotProperties telegramBotProperties,
                                 UpdateRepository updateRepository,
                                 CommandDispatcher commandDispatcher,
                                 NudeTextDispatcher nudeTextDispatcher) {
        super(telegramBotProperties);
        this.telegramBotProperties = telegramBotProperties;
        this.updateRepository = updateRepository;
        this.commandDispatcher = commandDispatcher;
        this.nudeTextDispatcher = nudeTextDispatcher;
    }

    @Transactional
    @Override
    public void distribute(DefaultAbsSender bot, Update update) {
        updateRepository.save(new UpdateEntity(update));
        UpdateTypeEnum updateType = DispatcherUtils.classifyUpdate(update);
        switch (updateType) {
            case COMMAND -> commandDispatcher.distribute(bot, update);
            case NUDE_TEXT -> nudeTextDispatcher.distribute(bot, update);
            case UNKNOWN -> TelegramBotUtils.handleUnknownUpdateType(update, bot);
        }
    }
}
