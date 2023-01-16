package com.example.apibasic.req;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
//클라이언트가 보낸 요청을 받을 수 있음
@Controller
@RequestMapping("/say")
public class ApiController1 {


    //요청을 받아서 처리할 메서드
    @RequestMapping(value = "/hello", method = {GET, POST})
    public String hello(HttpServletRequest request){
        log.info("hello spring  - {}", request.getMethod());
        log.trace("trace 로그~~~~~~~~");
        log.debug("debug 로그~~~~~~~~");
        log.info("info 로그~~~~~~~~");
        log.warn("warn 로그~~~~~~~~");
        log.error("error 로그~~~~~~~~");
        return "hello";
    }

    @PostMapping
    @RequestMapping(value = "/hello2", method = {GET})
    public ResponseEntity<?> hi(){
        return ResponseEntity.status(200).body("하이");
    }

    // GET요청만 따로 처리하겠다
//    @RequestMapping(value = "/greet", method = GET)
    @GetMapping("/greet")
    public String greet() {
        log.info("/greet GET 요청!!");
        return "";
    }

    @PostMapping("/greet")
    public String greet2() {
        log.info("/greet POST 요청!!");
        return "";
    }

    //요청헤더읽기
    @GetMapping("/header")
    public String header(HttpServletRequest request){
        String host = request.getHeader("Host");
        String accept = request.getHeader("accept");
        String pet = request.getHeader("pet");
        log.info("=========header info========");
        log.info("# header : {}", host);
        log.info("# header : {}", accept);
        log.info("# header : {}", pet);

        return "";
    }



}
