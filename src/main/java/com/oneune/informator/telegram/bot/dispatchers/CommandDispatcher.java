package com.oneune.informator.telegram.bot.dispatchers;

import com.oneune.informator.rest.services.BotService;
import com.oneune.informator.rest.store.exceptions.BusinessLogicException;
import com.oneune.informator.telegram.bot.abstracts.Dispatcher;
import com.oneune.informator.telegram.bot.store.enums.CommandEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class CommandDispatcher implements Dispatcher {

    BotService botService;

    @Override
    public void distribute(DefaultAbsSender bot, Update update) {

        String text = update.getMessage().getText();
        CommandEnum command = Arrays.stream(CommandEnum.values())
                .filter(cmd -> cmd.getValue().equals(text))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("Not found command constant like <%s>".formatted(text)));

        switch (command) {
            case START -> botService.execute(bot, update);
            case TRACK -> botService.sendTrackMessage(bot, update);
            case HISTORY_TRACK -> botService.sendHistoryTrackMessage(bot, update);
            default -> throw new IllegalArgumentException("Unknown CommandType enum constant!");
        }
    }
}
