package com.oneune.informator.rest.store.dtos.russian_mail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * История операций над отправлением.
 * <a href="https://tracking.pochta.ru/specification">Документация почты России</a>
 */
@XmlRootElement(
        name = "OperationHistoryData",
        namespace = "http://russianpost.org/operationhistory/data"
)
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class OperationHistoryDto {

    /**
     * Список операций над отправлением.
     */
    @XmlElement(name = "historyRecord", namespace = "http://russianpost.org/operationhistory/data")
    List<HistoryRecordDto> records;
}
