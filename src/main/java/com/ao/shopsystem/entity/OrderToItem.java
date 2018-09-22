package com.ao.shopsystem.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * The Entity for the ManyToMany relationship between {@link Order} and {@link Item}
 * Created by ao on 2018-09-21
 */
@Entity
@IdClass(CompositePK.class)
@Data
@Table(name = "order_item")
public class OrderToItem extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long quantity;
}

class CompositePK implements Serializable {

    private Order order;

    private Item item;
}
