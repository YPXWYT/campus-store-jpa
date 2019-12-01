package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_user")
@JsonIgnoreProperties(value = {"products", "roles", "orders", "addresses"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nick;
    private Character sex;
    @Column(unique = true)
    private String account;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(scale = 2)
    private Double money;
    @Column(name = "head_img")
    private String headImg;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String address;
    private Integer reputation;
    @Column(name = "confirm_status")
    private Integer confirmStatus;
    @Column(name = "inform_count")
    private Integer informCount;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<Product>();

    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<Role>();
    @OneToOne(mappedBy = "user")
    private Identification identification;

}
