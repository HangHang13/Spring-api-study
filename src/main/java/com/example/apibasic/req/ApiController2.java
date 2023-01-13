package com.example.apibasic.req;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class ApiController2 {

    @GetMapping("/param1")
    public String param1(
            String username, //파라미터 키값과 변수명이 같으면 어노테이션 생략 가능
            @RequestParam("age") int age
    ){
        log.info("/params? GET!! username={}, age={}",username,age);
        log.info("/params? GET!! 내이름은 {} 이고, 나이는 {} 이다.",username,age);

        return username;
    }

    @GetMapping("/param2")
    public String param2(OrderInfo orderInfo){
        log.info("/param2?={},{},{}",orderInfo.getOrderNo(),orderInfo.getAmount(),orderInfo.getGoodsName());
        log.info("주문정보 : {}", orderInfo);
        return "";
    }
    //요청 바디 읽기
    @PostMapping("/req-body")
    public String reqBody(@RequestBody OrderInfo orderInfo){
        log.info("=====주문정보======");
        log.info("# 주문 번호: {}", orderInfo.getOrderNo());
        log.info("# 주문 상품명: {}", orderInfo.getGoodsName());
        log.info("# 주문 양: {}", orderInfo.getAmount());


        return "";
    }




    //커맨드 객체
    //클라이언트가 보낸 파라미터 이름과 필드명이 정확히 일치해야함
    @Getter @Setter @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class OrderInfo{
        private Long orderNo;
        private String goodsName;
        private int amount;
    }


}
