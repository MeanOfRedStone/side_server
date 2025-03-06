package com.server.side.item;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Items")
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private int price;

    private String category;

    private String image;

    private String information;

    private String measurement;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;

    @Builder
    public Item(long id, String name, int price, String category,
                String image, String information, String measurement) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category  = category;
        this.image = image;
        this.information = information;
        this.measurement = measurement;
    }

}
