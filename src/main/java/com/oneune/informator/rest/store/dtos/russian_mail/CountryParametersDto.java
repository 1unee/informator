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
 * Содержит данные о стране места назначения пересылки отправления.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class CountryParametersDto {

    /**
     * Код страны.
     * Возможные коды приведены в поле "Код" справочника стран.
     */
    @XmlElement(name = "Id", namespace = "http://russianpost.org/operationhistory/data")
    Long id;

    /**
     * Двухбуквенный идентификатор страны.
     * Возможные идентификаторы приведены в поле "Alpha2 код" справочника стран.
     */
    @XmlElement(name = "Code2A", namespace = "http://russianpost.org/operationhistory/data")
    String code2A;

    /**
     * Трехбуквенный идентификатор страны.
     * Возможные идентификаторы приведены в поле "Alpha3 код" справочника стран.
     */
    @XmlElement(name = "Code3A", namespace = "http://russianpost.org/operationhistory/data")
    String code3A;

    /**
     * Российское название страны.
     * Возможные названия приведены в поле "Наименование страны пересылки" справочника стран.
     */
    @XmlElement(name = "NameRu", namespace = "http://russianpost.org/operationhistory/data")
    String russianName;

    /**
     * Международное название страны.
     * Возможные названия приведены в поле «Английское наименование страны пересылки» справочника стран.
     */
    @XmlElement(name = "NameEN", namespace = "http://russianpost.org/operationhistory/data")
    String internationalName;
}
