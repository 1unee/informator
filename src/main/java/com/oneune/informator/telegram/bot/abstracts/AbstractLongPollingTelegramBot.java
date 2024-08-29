package com.oneune.informator.telegram.bot.abstracts;

import com.oneune.informator.telegram.bot.configs.properies.TelegramBotProperties;
import com.oneune.informator.telegram.bot.configs.properies.TelegramBotProperties.BotMenuTypeEnum;
import com.oneune.informator.telegram.bot.utils.TelegramBotUtils;
import jakarta.ws.rs.NotAcceptableException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public abstract class AbstractLongPollingTelegramBot extends TelegramLongPollingBot implements Dispatcher {

    TelegramBotProperties telegramBotProperties;

    public AbstractLongPollingTelegramBot(TelegramBotProperties telegramBotProperties) {
        this.telegramBotProperties = telegramBotProperties;
        if (telegramBotProperties.getMenu().getType().equals(BotMenuTypeEnum.COMMANDS)) {
            registerBotCommands();
        } else {
            registerBotMenuButton();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        distribute(this, update);
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotProperties.getToken();
    }

    /**
     * By default, using commands from application.yml
     */
    protected void registerBotCommands() {
        List<BotCommand> botCommandsFromApplicationYml = telegramBotProperties.getMenu().getCommand()
                .entrySet()
                .stream()
                .map(entry -> new BotCommand(entry.getKey(), entry.getValue().getDescription()))
                .toList();
        registerBotCommands(botCommandsFromApplicationYml);
    }

    private void registerBotCommands(Collection<? extends BotCommand> botCommands) {
        SetMyCommands setMyCommands = SetMyCommands.builder()
                .commands(botCommands)
                .build();
        Boolean success = TelegramBotUtils.uncheckedExecute(this, setMyCommands);
        if (success) {
            log.info("Was registered {} bot commands", botCommands);
        } else {
            throw new NotAcceptableException("Not registered %s bot commands (see logs)".formatted(botCommands));
        }
    }

    private void registerBotCommands(BotCommand... botCommands) {
        List<BotCommand> botCommandList = Arrays.stream(botCommands).toList();
        registerBotCommands(botCommandList);
    }

    private void registerBotMenuButton() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton.builder()
                .menuButton(MenuButtonWebApp.builder()
                        .text(telegramBotProperties.getMenu().getButton().getTitle())
                        .webAppInfo(WebAppInfo.builder()
                                .url(telegramBotProperties.getMenu().getButton().getUrl().toString())
                                .build())
                        .build())
                .build();
        Boolean success = TelegramBotUtils.uncheckedExecute(this, setChatMenuButton);
        if (success) {
            log.info("Was registered {} bot menu button", setChatMenuButton);
        } else {
            throw new NotAcceptableException("Not registered %s bot menu button (see logs)".formatted(setChatMenuButton));
        }
    }
}
