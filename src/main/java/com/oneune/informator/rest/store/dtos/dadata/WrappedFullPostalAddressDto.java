package com.oneune.informator.rest.store.dtos.dadata;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class WrappedFullPostalAddressDto {

    /**
     * Индекс почтового отделения.
     * Например, 453115
     */
    @JsonAlias("value")
    String value;

    /**
     * Полный адрес в пределах страны.
     * Например, Респ Башкортостан, г Стерлитамак, ул Нагуманова, д 56Б
     */
    @JsonAlias("unrestricted_value")
    String unrestrictedValue;

    /**
     * Подробная информация об почтовом отделении.
     */
    @JsonAlias("data")
    FullPostalAddressDto data;
}

