package com.tna.campus_store.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sys_identification")
@JsonIgnoreProperties(value = {"user"})
public class Identification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "stu_number")
    private String stuNumber;
    private String name;
    @Column(name = "school_id")
    private Integer schoolId;
    @Column(name = "user_id")
    private Integer userId;
}
