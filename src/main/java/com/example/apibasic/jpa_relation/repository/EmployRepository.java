package com.example.apibasic.jpa_relation.repository;

import com.example.apibasic.jpa_relation.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployRepository extends JpaRepository<Employee, Long> {



}
