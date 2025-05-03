package com.server.side.item.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Items")
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer price;

    private String category;

    private String description;

    private String image;

    private List<String> information;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;

    @Builder
    public Item(Long id, String name, Integer price, String category, String description,
                String image, List<String> information) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category  = category;
        this.description = description;
        this.image = image;
        this.information = information;
    }

}
