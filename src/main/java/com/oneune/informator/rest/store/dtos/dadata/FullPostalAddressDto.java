package com.oneune.informator.rest.store.dtos.dadata;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class FullPostalAddressDto {

    /**
     * Индекс почтового отделения.
     * Например, 453115.
     */
    @JsonAlias("postal_code")
    String postalCode;

    /**
     * Признак закрытия объекта (false означает, что объект не закрыт)
     * Например, false
     */
    @JsonAlias("is_closed")
    Boolean isClosed;

    /**
     * Код типа объекта
     * Например, ГОПС
     */
    @JsonAlias("type_code")
    String typeCode;

    /**
     * Полный адрес в строковом формате
     * Например, "Респ Башкортостан, г Стерлитамак, ул Нагуманова, д 56Б"
     * М
     */
    @JsonAlias("address_str")
    String addressStr;

    /**
     * Код КЛАДР для адреса
     * Например, 0200001400000
     */
    @JsonAlias("address_kladr_id")
    String addressKladrId;

    /**
     * Качество адреса (0 - точный адрес)
     */
    @JsonAlias("address_qc")
    Integer addressQc;

    /**
     * Широта географического положения
     * Например, 53.631019
     */
    @JsonAlias("geo_lat")
    BigDecimal geoLat;

    /**
     * Долгота географического положения
     * Например, 55.958303
     */
    @JsonAlias("geo_lon")
    BigDecimal geoLon;

    /**
     * График работы на понедельник
     * Например, "09:00-18:00"
     */
    @JsonAlias("schedule_mon")
    String scheduleMon;

    /**
     * График работы на вторник
     */
    @JsonAlias("schedule_tue")
    String scheduleTue;

    /**
     * График работы на среду
     */
    @JsonAlias("schedule_wed")
    String scheduleWed;

    /**
     * График работы на четверг
     */
    @JsonAlias("schedule_thu")
    String scheduleThu;

    /**
     * График работы на пятницу
     * Например,
     */
    @JsonAlias("schedule_fri")
    String scheduleFri;

    /**
     * График работы на субботу
     * Например,
     */
    @JsonAlias("schedule_sat")
    String scheduleSat;

    /**
     * График работы на воскресенье (может быть null)
     */
    @JsonAlias("schedule_sun")
    @Nullable String scheduleSun;
}