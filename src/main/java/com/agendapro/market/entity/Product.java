package com.agendapro.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
@CompoundIndex(def = "{'name':1}")
public class Product {
    @Id
    private String id;

    // validate duplicate
    @Indexed(unique = true)
    private String name;

    private BigDecimal price;

    private Instant createdAt;

    private Instant updatedAt;
}
