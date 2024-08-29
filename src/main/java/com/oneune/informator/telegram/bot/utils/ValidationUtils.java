package com.oneune.informator.telegram.bot.utils;

import com.oneune.informator.telegram.bot.configs.properies.TelegramBotProperties;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

@UtilityClass
public class ValidationUtils {

    public void validateToken(String token) {
        Optional.ofNullable(token).orElseThrow(() -> new IllegalArgumentException(
                """
                Token property is necessary!
                
                Fill property by this path:
                
                telegram:
                    bot:
                        token: <YOUR_BOT_TOKEN>
                """
        ));
    }

    public void validateCommands(Map<String, TelegramBotProperties.TelegramBotCommandProperties> command) {
        boolean commandsValid = command.keySet().stream().allMatch(commandName -> commandName.startsWith("/"));
        if (commandsValid) {
            throw new IllegalArgumentException("""
                    Commands can not start with «/» (slash sign).
                    A slash is added programmatically.
                    """);
        }
    }
}
