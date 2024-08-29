package com.oneune.informator.telegram.bot.configs.properies;

import com.oneune.informator.telegram.bot.utils.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;
import java.util.Map;

@ConfigurationProperties(prefix = "telegram.bot")
@PropertySource("classpath:application.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TelegramBotProperties {

    /**
     * Optional property.
     * Name of telegram bot.
     */
    String name = "default-telegram-bot";

    /**
     * Necessary property.
     * Generated token telegram bot.
     */
    String token;

    /**
     * Optional property.
     * Used for setting webhook.
     */
    String url;

    BotMenu menu;

    @PostConstruct
    public void init() {
        ValidationUtils.validateToken(token);
        ValidationUtils.validateCommands(menu.command);
    }

    public enum BotMenuTypeEnum {
        BUTTON,
        COMMANDS
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    public static class BotMenu {

        BotMenuTypeEnum type = BotMenuTypeEnum.COMMANDS;

        /**
         * Telegram Bot command properties.
         * For example:
         *   command:
         *     name-of-your-command:
         *       description: description-of-your-command
         */
        Map<String, TelegramBotCommandProperties> command = Map.of("start", new TelegramBotCommandProperties());

        TelegramBotMenuButton button;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    public static class TelegramBotCommandProperties {
        String description = "This is a start command.";
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    public static class TelegramBotMenuButton {
        String title;
        URI url;
    }
}
