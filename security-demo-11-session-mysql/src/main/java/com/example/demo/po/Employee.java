package com.example.demo.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 */
@Entity
@Data
@Table(name = "t_employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;



}
