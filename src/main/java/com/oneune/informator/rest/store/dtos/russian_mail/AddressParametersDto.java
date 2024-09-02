package com.oneune.informator.rest.store.dtos.russian_mail;

import com.oneune.informator.rest.store.dtos.dadata.FullPostalAddressDto;
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
 * Содержит адресные данные с операцией над отправлением.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class AddressParametersDto {

    /**
     * Содержит адресные данные места назначения пересылки отправления.
     */
    @XmlElement(name = "DestinationAddress", namespace = "http://russianpost.org/operationhistory/data")
    AddressDto destination;

    /**
     * Подробная информация о месте назначения почтового отделения.
     * Информация берется путем интеграции с Dadata-сервисом.
     */
    FullPostalAddressDto postalDestination;

    /**
     * Содержит данные о стране места назначения пересылки отправления.
     */
    @XmlElement(name = "MailDirect", namespace = "http://russianpost.org/operationhistory/data")
    CountryParametersDto destinationCountryParameters;

    /**
     * Содержит адресные данные места проведения операции над отправлением.
     */
    @XmlElement(name = "OperationAddress", namespace = "http://russianpost.org/operationhistory/data")
    AddressDto departure;

    /**
     * Подробная информация о месте отправления почтового отделения.
     * Информация берется путем интеграции с Dadata-сервисом.
     */
    FullPostalAddressDto postalDeparture;

    /**
     * Содержит данные о стране места назначения пересылки отправления.
     */
    @XmlElement(name = "CountryFrom", namespace = "http://russianpost.org/operationhistory/data")
    CountryParametersDto departureCountryParameters;

//    /**
//     * Содержит данные о стране проведения операции над почтовым отправлением. DEPRECATED
//     */
//    @XmlElement(name = "CountryOper", namespace = "http://russianpost.org/operationhistory/data")
//    CountryParametersDto processingCountryParameters;
}
