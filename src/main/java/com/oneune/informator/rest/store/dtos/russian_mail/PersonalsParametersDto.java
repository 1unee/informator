package com.oneune.informator.rest.store.dtos.russian_mail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Содержит данные субъектов, связанных с операцией над почтовым отправлением.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class PersonalsParametersDto {

    /**
     * Содержит информацию о категории отправителя.
     */
    @XmlElement(name = "SendCtg", namespace = "http://russianpost.org/operationhistory/data")
    DepartureCategoryDto departureCategory;

    /**
     * Содержит данные об отправителе.
     * Пример значения: ИВАНОВ А Н
     */
    @XmlElement(name = "Sndr", namespace = "http://russianpost.org/operationhistory/data")
    String departurePersonal;

    /**
     * Содержит данные о получателе отправления.
     * Пример значения: ПЕТРОВ И. К.
     */
    @XmlElement(name = "Rcpn", namespace = "http://russianpost.org/operationhistory/data")
    String destinationPersonal;
}
