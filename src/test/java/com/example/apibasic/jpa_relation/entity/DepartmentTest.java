package com.example.apibasic.jpa_relation.entity;

import com.example.apibasic.ApibasicApplication;
import com.example.apibasic.jpa_relation.repository.DepartmentRepository;
import com.example.apibasic.jpa_relation.repository.EmployRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = ApibasicApplication.class)
class DepartmentTest {


    @Autowired
    EmployRepository employRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void empTest1(){

        Department department1 = Department.builder()
                .deptName("개발부")
                .build();

        Department department2 = Department.builder()
                .deptName("영업부")
                .build();


        Employee employee1 = Employee.builder()
                .empName("푸파파파")
                .department(department1)
                .build();


        Employee employee2 = Employee.builder()
                .empName("헬로키티")
                .department(department1)
                .build();


//
//        departmentRepository.save(department1);
//        departmentRepository.save(department2);

        employRepository.save(employee1);
        employRepository.save(employee2);

    }

    @Test
    @Transactional
    void empTest(){
        Employee employee = employRepository.findById(2L).orElseThrow();
        System.out.println("employ" + employee.getDepartment().getDeptName());


    }

    //트랜잭션 all of nothing
    @Test
    @Transactional
    void empTest3() {

        Department dept
                = departmentRepository.findById(1L)
                .orElseThrow();

        List<Employee> employees = dept.getEmployees();

        employees.forEach(System.out::println);
    }
}