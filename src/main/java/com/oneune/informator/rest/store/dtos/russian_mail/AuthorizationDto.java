package com.oneune.informator.rest.store.dtos.russian_mail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Авторизационный заголовок.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class AuthorizationDto {

    @XmlElement(name = "login")
    private String login;

    @XmlElement(name = "password")
    private String password;
}
