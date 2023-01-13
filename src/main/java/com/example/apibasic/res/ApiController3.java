package com.example.apibasic.res;


import com.example.apibasic.req.ApiController2;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@Controller
//@ResponseBody 이거 두개 합친게 restcontroller
@RestController
@Slf4j
public class ApiController3 {
    @PostMapping("/res1")
    public String res1(){
        log.info("/res1 GET!!!");
        return "";
    }


    //produces = "application/json" 이 숨어있어서 json으로 변환했던거, consumes는 ~~만 받길 원한다.
    @GetMapping(value = "/res2", produces = "application/json", consumes = "application/json")
    public List<String> res2(){
        log.info("/res2 GET!!!");
        return List.of("짜장면","볶음밥","탕수육");
    }

    @GetMapping(value = "/res3", produces = "application/json")
    public ApiController2.OrderInfo res3(){
        log.info("/res3 GET!!!");
        ApiController2.OrderInfo orderInfo = new ApiController2.OrderInfo();
        orderInfo.setOrderNo(12L);
        orderInfo.setGoodsName("자장면");
        orderInfo.setAmount(10);
        return orderInfo;
    }

    //사원 목록 정보
    @GetMapping("/employee")
    public List<Employee> empList(){
        return List.of(
                new Employee("홍길동","영업부",LocalDate.of(2018,12,4)),
                new Employee("김혁","영업부",LocalDate.of(2017,1,24)),
                new Employee("홍길동","영업부",LocalDate.of(2018,12,14))
        );

    }
    //응답시에 응답헤더정보와 응답상태코드를 조작하려면 ResponseEntity 객체 사용

    @GetMapping("/res4")
    public ResponseEntity<?> res4(String nick, String deptName){
        if ((nick == null) || nick.equals("")){
            return ResponseEntity.badRequest().build();
        }

        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("department", deptName);
        headers.set("blah-blah", "hahahahahaha");
        return ResponseEntity
                .ok()
//                .status(200)
//                .status(HttpStatus.OK)
                .headers(headers).body(new Employee(nick,deptName,LocalDate.now()));
    }

    //커맨드 객체
    @Getter @Setter @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class Employee{
        private String enpName;
        private String deptName;
        private LocalDate hireDate;
    }
}
