package com.oneune.informator.rest.store.dtos.russian_mail;

import jakarta.annotation.Nullable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Содержит данные о почтовом отправлении.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class ParcelParametersDto {

    /**
     * Идентификатор почтового отправления, текущий для данной операции (трек-номер).
     */
    @XmlElement(name = "Barcode", namespace = "http://russianpost.org/operationhistory/data")
    String barcode;

    /**
     * Служебная информация, идентифицирующая отправление,
     * может иметь значение ДМ квитанции,
     * связанной с отправлением или иметь значение <null>
     */
    @XmlElement(name = "Internum", namespace = "http://russianpost.org/operationhistory/data")
    @Nullable
    String hash;

    /**
     * Признак корректности вида и категории отправления для внутренней пересылки.
     */
    @XmlElement(name = "ValidRuType", namespace = "http://russianpost.org/operationhistory/data")
    Boolean russianValid;

    /**
     * Признак корректности вида и категории отправления для международной пересылки.
     */
    @XmlElement(name = "ValidEnType", namespace = "http://russianpost.org/operationhistory/data")
    Boolean internationalValid;

    /**
     * Содержит текстовое описание вида и категории отправления.
     */
    @XmlElement(name = "ComplexItemName", namespace = "http://russianpost.org/operationhistory/data")
    String generalDescription;

    /**
     * Содержит информацию о разряде почтового отправления.
     */
    @XmlElement(name = "MailRank", namespace = "http://russianpost.org/operationhistory/data")
    MailRankDto rank;

    /**
     * Содержит информацию об отметках почтовых отправлений.
     */
    @XmlElement(name = "PostMark", namespace = "http://russianpost.org/operationhistory/data")
    PostMarkDto mark;

    /**
     * Содержит данные о виде почтового отправления.
     */
    @XmlElement(name = "MailType", namespace = "http://russianpost.org/operationhistory/data")
    MailTypeDto type;

    /**
     * Содержит данные о категории почтового отправления.
     */
    @XmlElement(name = "MailCtg", namespace = "http://russianpost.org/operationhistory/data")
    MailCategoryDto category;

    /**
     * Вес отправления в граммах.
     */
    @XmlElement(name = "Mass", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal weight;

    /**
     * Значение максимально возможного веса для данного вида и категории отправления для внутренней пересылки.
     */
    @XmlElement(name = "MaxMassRu", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal maxWeightRussian;

    /**
     * Значение максимально возможного веса для данного вида и категории отправления для международной пересылки.
     */
    @XmlElement(name = "MaxMassEn", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal maxWeightInternational;
}
