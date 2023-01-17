package com.example.apibasic.jpabasic.repository;

// junit5에서는 클래스, 메서드, 필드 default제한만을 허용

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 스프링 컨테이너를 사용해서 스프링이 관리하는 객체를 주입받는 기능
class MemberRepositoryTest {

    // 스프링 빈을 주입받을 때 필드주입을 사용
    @Autowired
    MemberRepository memberRepository;


    // @BeforeEach 각 테스트를 실행하기 전에 실행되는 내용
    @BeforeEach
    void bulkInsert(){
        MemberEntity saveMember1 = MemberEntity.builder()
                .account("abc4321")
                .password("4321")
                .nickName("궁예")
                .gender(Gender.MALE)
                .build();
        MemberEntity saveMember2 = MemberEntity.builder()
                .account("ppp9999")
                .password("9999")
                .nickName("찬호박")
                .gender(Gender.FEMALE)
                .build();
        MemberEntity saveMember3 = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(Gender.FEMALE)
                .build();
        MemberEntity saveMember4 = MemberEntity.builder()
                .account("as14341")
                .password("1234")
                .nickName("칰흰")
                .gender(Gender.FEMALE)
                .build();
        MemberEntity saveMember5 = MemberEntity.builder()
                .account("gqel")
                .password("1234")
                .nickName("지지")
                .gender(Gender.MALE)
                .build();
        MemberEntity saveMember6 = MemberEntity.builder()
                .account("gvadv")
                .password("1234")
                .nickName("박김")
                .gender(Gender.MALE)
                .build();


        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);
        memberRepository.save(saveMember4);
        memberRepository.save(saveMember5);
        memberRepository.save(saveMember6);
    }




    // 테스트 메서드
    // 테스트는 여러번 돌려도 성공한 테스트는 계속 성공해야 한다.
    // 단언 (Assertion) : 강력히 주장한다.
    @Test
    @DisplayName("회원의 가입 정보를 데이터베이스에 저장해야 한다.")
    @Transactional
    @Rollback //테스트가 끝나면 롤백한다.
    void saveTest() {
        // given - when - then 패턴
        // given : 테스트시 주어지는 데이터
        MemberEntity saveMember = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(Gender.FEMALE)
                .build();
        // when : 실제 테스트 상황
        memberRepository.save(saveMember); // insert쿼리 실행

        Optional<MemberEntity> foundMember = memberRepository.findById(1L);// pk기반 단일 행 조회

        // then : 테스트 결과 단언
        // 회원이 조회되었을 것이다.
        MemberEntity member = foundMember.get();
        assertNotNull(member);

        // 그 저장된 회원의 user_code는 몇번? => 1번
        // param1: 내 기대값,  param2: 실제값
        //autoincrement로 id값은 계속 더해짐 단언하지말것
//        assertEquals(1L, member.getUserId());

        // 저장된 회원의 닉네임은 뭘까요?  => 꾸러긔
        assertEquals("꾸러긔", member.getNickName());
    }

    //목록조회 테스트


    @Test
    @DisplayName("회원 목록을 조회하면 3명의 회원정보가 조회되어야 한다.")
    @Transactional
    @Rollback
    void findAllTest(){
        //given
//        MemberEntity saveMember1 = MemberEntity.builder()
//                .account("abc4321")
//                .password("4321")
//                .nickName("궁예")
//                .gender(Gender.MALE)
//                .build();
//        MemberEntity saveMember2 = MemberEntity.builder()
//                .account("ppp9999")
//                .password("9999")
//                .nickName("찬호박")
//                .gender(Gender.FEMALE)
//                .build();
//        MemberEntity saveMember3 = MemberEntity.builder()
//                .account("zzz1234")
//                .password("1234")
//                .nickName("꾸러긔")
//                .gender(Gender.FEMALE)
//                .build();
        //when


        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //then
        //조회된 리스트의 사이즈는 3이어야 한다.
        Assertions.assertEquals(3, memberEntityList.size());

        //조회된 리스트의 1번째 회원 닉네임은 궁예여야한다.
        Assertions.assertEquals("궁예", memberEntityList.get(0).getNickName());


    }

    @Test
    @DisplayName("회원 데이터를 3개 등록하고 그 중 하나의 회원을 삭제해야 한다.")
    @Transactional
    @Rollback
    void deleteTest() {
        // given
        Long userCode = 2L;
        // when
        memberRepository.deleteById(userCode);
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);

        // then
        assertFalse(foundMember.isPresent());
        assertEquals(2, memberRepository.findAll().size());

    }

    @Test
    @DisplayName("2번 회원의 닉네임과 성별을 수정해야한다.")
    @Transactional
    @Rollback
    void update(){
    //given
    Long userCode = 2L;
    String newNickName = "닭강정";

    Gender newGender = Gender.MALE;

    //when
    //jpa에서 수정은 조회후 setter로 변경
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);
        foundMember.ifPresent(m->{
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        });

        Optional<MemberEntity> newMember = memberRepository.findById(userCode);
        Assertions.assertEquals("닭강정", foundMember.get().getNickName());
        Assertions.assertEquals(newGender, foundMember.get().getGender());
    }

    @Test
    @DisplayName("쿼리메서드를 사용하여 여성회원만 조회하도록 한다.")
    @Transactional
    @Rollback
    void findByGenderTest(){
        //given
        Gender gender = Gender.FEMALE;

        //when
        List<MemberEntity> foundMember = memberRepository.findByGender(gender);

        //then
        foundMember.forEach(m->
                        Assertions.assertTrue(m.getGender() == Gender.FEMALE)
                );
        }


        @Test
        @DisplayName("쿼리메서드를 사용하여 계정과 성별로 조회하도록 한다.")
        @Transactional
        @Rollback
        void findByAccountAndGenderTest(){
            //given
            Gender gender = Gender.MALE;
            String account = "abc4321";
            //when
            List<MemberEntity> foundMember = memberRepository.findByAccountAndGender(account,gender);

            //then
            foundMember.forEach(m->{
                    Assertions.assertTrue(m.getGender() == Gender.MALE);
                    Assertions.assertEquals("abc4321", m.getAccount());}
            );
        }

    @Test
    @DisplayName("쿼리메서드를 사용하여 이름에 박이 포함된 사람만 조회하도록 한다.")
    @Transactional
    @Rollback
    void findByNickNameContainingTest(){
        //given

        String nickName= "박";
        //when
        List<MemberEntity> foundMember = memberRepository.findByNickNameContaining(nickName);

        //then
        foundMember.forEach(m->{
            Assertions.assertTrue(m.getNickName().contains("박"));}
        );
    }

    @Test
    @DisplayName("JPQL을 사용하여 계정명이 zzz1234 인사람만 조회하도록 한다.")
    @Transactional
    @Rollback
    void getMemberByAccountTest(){
        //given

        String account= "zzz1234";
        //when
        MemberEntity foundMember = memberRepository.getMemberByAccount(account);

        //then
        Assertions.assertEquals("zzz1234", foundMember.getAccount());
    }

    @Test
    @DisplayName("JPQL을 사용하여 닉네임이 궁예면서 성별이 남성인사람만 조회하도록 한다.")
    void getMembersByNickAndGenderTest(){
        //given

        String nickName= "궁예";
        Gender gender = Gender.MALE;
        //when

        List<MemberEntity> list = memberRepository.getMembersByNickAndGender(nickName,gender);
        //then
        list.forEach(m -> {
            System.out.println(m);
            assertEquals("궁예", m.getNickName());
            assertEquals(Gender.MALE, m.getGender());
        });
    }



    @Test
    @DisplayName("JPQL을 사용하여 닉네임에 궁이 들어간사람만 조회하도록 한다.")
    void getMemberByNickNameTest(){
        //given

        String nickName= "궁";

        //when

        List<MemberEntity> list = memberRepository.getMemberByNickName(nickName);
        //then
        list.forEach(m -> {
            System.out.println(m);
            assertTrue(m.getNickName().contains("궁"));
        });
    }
    @Test
    @DisplayName("JPQL 을 사용해서 이름에 '박바보'인 회원을 삭제해야 한다.")
    void deleteByNickNameTest() {
        // given
        String nickName = "박김";

        // when
        memberRepository.deleteByNickName(nickName);

        // then
        List<MemberEntity> list = memberRepository.findAll();

            list.forEach(m->
                assertFalse(m.getNickName().contains("박김"))
                );

    }
}