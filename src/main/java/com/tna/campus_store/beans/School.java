package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_school")
@JsonIgnoreProperties(value = {"apartments", "products"})
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER)
    private Set<Apartment> apartments = new HashSet<Apartment>();
}
