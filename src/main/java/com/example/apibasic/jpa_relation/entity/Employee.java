package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "empId")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId; // 사원 번호

    private String empName;



    /*
    *
    * EAGER : 항상 조인을 하도록 설정 (디폴트)
    * LAZY : 부서정보가 필요할 때만 조인 (실무에서는 ManyToOne을 할때 무조건 LAZY)
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_Id") //fk 칼럼 이름 지정
    private Department department;


}
