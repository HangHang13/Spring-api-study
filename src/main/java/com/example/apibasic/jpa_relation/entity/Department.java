package com.example.apibasic.jpa_relation.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "dept_Id")
@Entity
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    private String deptName;


    //양방향 매핑에서는
    @OneToMany(mappedBy = "department")
    private List<Employee>employees = new ArrayList<>();

}
