package com.tna.campus_store.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_classification")
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "classification")
    private Set<Product> products = new HashSet<Product>();
}
