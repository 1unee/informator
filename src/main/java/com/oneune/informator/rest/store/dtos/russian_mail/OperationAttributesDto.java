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
 * Атрибут операции.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class OperationAttributesDto {

    /**
     *
     */
    @XmlElement(name = "Id", namespace = "http://russianpost.org/operationhistory/data")
    Long id;

    /**
     *
     */
    @XmlElement(name = "Name", namespace = "http://russianpost.org/operationhistory/data")
    String name;
}
