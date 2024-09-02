package com.oneune.informator.rest.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_history")
@SequenceGenerator(sequenceName = "request_history_id_seq", name = "id_seq", allocationSize = 1)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class RequestHistoryEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    UserEntity user;

    LocalDateTime timestamp;
    String barcode;
}
