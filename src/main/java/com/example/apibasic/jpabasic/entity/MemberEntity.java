package com.example.apibasic.jpabasic.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")  //유저아이디 중복만 비교
@Entity
@Table(name="member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //기본키 생성 전략
    @Column(name="member_id")
    private Long userId; //회원 식별 코드(기본키)

    @Column(nullable = false, name="account", unique = true, length = 30) //not null, unique , 글자수 30제한
    private String account;

    @Column(name="password",nullable = false)
    private String password;
    @Column(name="member_nickName" , nullable = false)
    private String nickName;
    @Column(name="gender")
    @Enumerated(EnumType.STRING) // ORDINNAL 순번(0부터 시작), STRING: 순수 문자열
    private Gender gender; //성별

    @CreationTimestamp  // INSERT되는 시점에 서버시간을 자동으로 입력
    @Column(name="joinDate")
    private LocalDateTime joinDate; //가입일자

    @UpdateTimestamp //UPDATE 시점에 서버시간을 자동으로 입력
    @Column(name="modifyDate")
    private LocalDateTime modifyDate; //정보 수정시간
}
