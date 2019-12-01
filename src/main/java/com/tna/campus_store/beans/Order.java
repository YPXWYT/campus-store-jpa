package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "sys_order")
@JsonIgnoreProperties(value = {"user", "product"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "carrier_id")
    private Integer carrierId;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    private Integer status;
    private Integer count;
    @Column(scale = 2)
    private Double total;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "product_id")
    private Integer ProId;
}
