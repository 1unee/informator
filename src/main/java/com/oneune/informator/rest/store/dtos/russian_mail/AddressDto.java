package com.oneune.informator.rest.store.dtos.russian_mail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Адрес.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class AddressDto {

    /**
     * Почтовый индекс места назначения.
     * Не возвращается для зарубежных операций.
     */
    @XmlElement(name = "Index", namespace = "http://russianpost.org/operationhistory/data")
    String index;

    /**
     * Адрес и/или название места назначения.
     */
    @XmlElement(name = "Description", namespace = "http://russianpost.org/operationhistory/data")
    String description;
}
