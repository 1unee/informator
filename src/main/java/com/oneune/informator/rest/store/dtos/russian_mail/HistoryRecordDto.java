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
 * Запись истории операции.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class HistoryRecordDto {

    /**
     * Содержит адресные данные с операцией над отправлением.
     */
    @XmlElement(name = "AddressParameters", namespace = "http://russianpost.org/operationhistory/data")
    AddressParametersDto addressParameters;

    /**
     * Содержит финансовые данные, связанные с операцией над почтовым отправлением.
     */
    @XmlElement(name = "FinanceParameters", namespace = "http://russianpost.org/operationhistory/data")
    FinanceParametersDto financeParameters;

    /**
     * Содержит данные о почтовом отправлении.
     */
    @XmlElement(name = "ParcelParameters", namespace = "http://russianpost.org/operationhistory/data")
    ParcelParametersDto parcelParameters;

    /**
     * Содержит параметры операции над отправлением
     */
    @XmlElement(name = "OperationParameters", namespace = "http://russianpost.org/operationhistory/data")
    OperationParametersDto operationParameters;

    /**
     * Содержит данные субъектов, связанных с операцией над почтовым отправлением.
     */
    @XmlElement(name = "UserParameters", namespace = "http://russianpost.org/operationhistory/data")
    PersonalsParametersDto personalsParameters;
}
