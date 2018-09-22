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
import lombok.EqualsAndHashCode;

/**
 * The Entity for the ManyToMany relationship between {@link Order} and {@link Product}
 * Created by ao on 2018-09-21
 */
@Entity
@IdClass(CompositePK.class)
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lineItem")
public class LineItem extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long quantity;
}

class CompositePK implements Serializable {

    private Order order;

    private Product product;
}
