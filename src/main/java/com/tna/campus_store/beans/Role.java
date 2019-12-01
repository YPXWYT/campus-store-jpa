package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_role")
@JsonIgnoreProperties(value = {"users"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)  //配置多表关系
    private Set<User> users = new HashSet<User>();
}
