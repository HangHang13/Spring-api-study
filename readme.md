[toc]



# start spring api

# 스프링 부트 프로젝트 설정
1. [프로젝트 생성 링크](https://start.spring.io)
2. HELP.md -> README.md로 이름변경
3. application.properties -> application.yml로 이름 변경
4. yml 설정으로 톰캣 포트 변경하기
```
server:
  port: 8181
```
5. devTools 설정
- 파일 -> 설정 -> 빌드,실행, 배포 -> 컴파일러 -> 프로젝트 자동빌드 체크
- 파일 -> 설정 -> 고급설정 -> 컴파일러 -> 개발된 애플리케이션 ~~~ auto-make 체크

## 로그 레벨 정리
1. trace : 코드를 추적하고 기능적 부분을 찾을 때
2. debug : 코드 블록의 문제를 점검하고 진단하고 해결할 때 도움이 되는 정보
3. info : 표준 로그 레벨, 시버스 시작이나 종료 정보
4. warn : 에러는 아닌데 좀 수상한 부분
5. error : 애플리케이션이 작동하지 않는 수준의 정보
6. fatal : 심각한 오류





# 0118

## @Transactional

- 원자성
- all of nothing
- a1 -> a2 -> a3
- a2 실패하면 a1 취소
- 모두 성공하면 커밋





## Lazy VS Eagar

```
EAGER : 항상 조인을 하도록 설정 (디폴트)
LAZY : 부서정보가 필요할 때만 조인 (실무에서는 ManyToOne을 할때 무조건 LAZY
```

@ManyToOne에서는 Lazy권장

Lazy는 필요할 때만 join쿼리 날림

Eager은 낭비가 심함

1. Lazy의 쿼리

   ```java
   select
           employee0_.emp_id as emp_id1_1_0_,
           employee0_.dept_id as dept_id3_1_0_,
           employee0_.emp_name as emp_name2_1_0_ 
       from
           employee employee0_ 
       where
           employee0_.emp_id=?
               
   Hibernate: 
       select
           employee0_.emp_id as emp_id1_1_0_,
           employee0_.dept_id as dept_id3_1_0_,
           employee0_.emp_name as emp_name2_1_0_ 
       from
           employee employee0_ 
       where
           employee0_.emp_id=?
   ```

   

2. Eagar의 쿼리

   ```
   select
           employee0_.emp_id as emp_id1_1_0_,
           employee0_.dept_id as dept_id3_1_0_,
           employee0_.emp_name as emp_name2_1_0_,
           department1_.dept_id as dept_id1_0_1_,
           department1_.dept_name as dept_nam2_0_1_ 
       from
           employee employee0_ 
       left outer join
           department department1_ 
               on employee0_.dept_id=department1_.dept_id 
       where
           employee0_.emp_id=?
           
   Hibernate: 
       select
           employee0_.emp_id as emp_id1_1_0_,
           employee0_.dept_id as dept_id3_1_0_,
           employee0_.emp_name as emp_name2_1_0_,
           department1_.dept_id as dept_id1_0_1_,
           department1_.dept_name as dept_nam2_0_1_ 
       from
           employee employee0_ 
       left outer join
           department department1_ 
               on employee0_.dept_id=department1_.dept_id 
       where
           employee0_.emp_id=?
   ```





## 매핑

- 단방향 매핑이 기본이다.
- 1:m의 경우 m에서만 1 fk로 참조 
- Jpa에서는 양방향 매핑이 있다.

### 양방향 매핑

- 양팡향 매핑에서는 상대방 엔티티의 정보를 수정할 수는 없고
- 단순읽기 기능(조회)만 지원한다.
- mappedBy에는 상대방 엔티티의 조인되는 필드명을 



```java
@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_Id") //fk 칼럼 이름 지정
    private Department department;
```

```java
@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee>employeeList = new ArrayList<>();
```

- Department **department** 이 이름을 써야