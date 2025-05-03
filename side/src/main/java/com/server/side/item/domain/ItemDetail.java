package com.server.side.item.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "ItemDetails")
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class ItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private String option;

    private Integer quantity;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;

    @Builder
    public ItemDetail(Long id, Item item, String option, Integer quantity) {
        this.id = id;
        this.item = item;
        this.option = option;
        this.quantity = quantity;
    }

}
