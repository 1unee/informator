package com.oneune.informator.rest.store.dtos.russian_mail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Содержит параметры операции над отправлением
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class OperationParametersDto {

    /**
     * Содержит информацию об операции над отправлением.
     */
    @XmlElement(name = "OperType", namespace = "http://russianpost.org/operationhistory/data")
    OperationTypeDto type;

    /**
     * Содержит информацию об атрибуте операции над отправлением.
     */
    @XmlElement(name = "OperAttr", namespace = "http://russianpost.org/operationhistory/data")
    OperationAttributesDto attributes;

    /**
     * Содержит данные о дате и времени проведения операции над отправлением.
     * Пример значения: 2015-01-08T14:50:00.000+03:00.
     */
    @XmlElement(name = "OperDate", namespace = "http://russianpost.org/operationhistory/data")
    String stringifiedTimestamp;
}
