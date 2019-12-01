package com.tna.campus_store.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sys_student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "school_name")
    private String schoolName;
    @Column(name = "stu_number")
    private String stuNumber;
    private String name;
}
