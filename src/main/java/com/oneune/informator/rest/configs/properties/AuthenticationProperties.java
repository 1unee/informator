package com.oneune.informator.rest.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AuthenticationProperties {
    String login;
    String password;
    String token;
}
