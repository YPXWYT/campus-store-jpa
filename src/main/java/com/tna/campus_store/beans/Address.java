package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sys_address")
@JsonIgnoreProperties(value = {"user"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String addrString;
    private String phoneNumber;
    private Boolean isDefault;
    private String remark;
    @Column(name = "user_id")
    private Integer userId;
}
