package com.oneune.informator.rest.store.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public abstract class AbstractDto implements Identifiable {
    @Setter(AccessLevel.PRIVATE)
    Long id;
}
