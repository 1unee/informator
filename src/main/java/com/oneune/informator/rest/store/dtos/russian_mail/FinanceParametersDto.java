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

import java.math.BigDecimal;

/**
 * Содержит финансовые данные, связанные с операцией над почтовым отправлением.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class FinanceParametersDto {

    /**
     * Сумма наложенного платежа в копейках.
     */
    @XmlElement(name = "Payment", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal payment;

    /**
     * Сумма объявленной ценности в копейках.
     */
    @XmlElement(name = "Value", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal value;

    /**
     * Общая сумма платы за пересылку наземным и воздушным транспортом в копейках.
     */
    @XmlElement(name = "MassRate", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal weightRate;

    /**
     * Сумма платы за объявленную ценность в копейках.
     */
    @XmlElement(name = "InsrRate", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal excessRate;

    /**
     * Выделенная сумма платы за пересылку воздушным транспортом из общей суммы платы за пересылку в копейках.
     */
    @XmlElement(name = "AirRate", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal airRate;

    /**
     * Сумма дополнительного тарифного сбора в копейках.
     */
    @XmlElement(name = "Rate", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal rate;

    /**
     * Сумма таможенного платежа в копейках.
     */
    @XmlElement(name = "CustomDuty", namespace = "http://russianpost.org/operationhistory/data")
    BigDecimal customDuty;
}
