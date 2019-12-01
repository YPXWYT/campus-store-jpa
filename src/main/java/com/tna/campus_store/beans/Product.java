package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_product")
@Data
@JsonIgnoreProperties(value = {"user", "orders", "classification", "school"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer sort;
    @Column(name = "purchase_price", scale = 2)
    private Double purchasePrice;
    @Column(name = "sell_price", scale = 2)
    private Double sellPrice;
    private Integer count;
    private String des;
    private Integer status;
    private String img;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    @Column(name = "use_time")
    private Integer useTime;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<Order>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Classification classification;
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
}
