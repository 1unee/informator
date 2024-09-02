package com.oneune.informator.rest.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.net.URI;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public abstract class AbstractIntegrationProperties {
    AuthenticationProperties authentication;
    URI url;
}
