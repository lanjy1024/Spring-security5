package com.example.demo.dao;

import com.example.demo.po.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByName(String name);

}
