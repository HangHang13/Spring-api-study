package com.example.apibasic.jpabasic.repository;

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

//jpa CRUD하려면 JpaRepository 인터페이스 상속
//제네릭타입으로 첫번째로 CRUD할 엔티티크래스의 타입, 두번째로 해당 엔티티의 id의 타입
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //쿼리 메서드


    List<MemberEntity> findByGender(Gender gender);

    List<MemberEntity> findByAccountAndGender(String account, Gender gender);

    List<MemberEntity> findByNickNameContaining(String nickName);

    //JPQL 사용
    // select 별칭 from 엔터티클래스명 as 별칭 where 별칭, 필드명
    // native-sql : select m.userCode from tbl_member as m
    // jpql : select m.userId from MemberEntity as m
    //계정명으로 회원 조회
    @Query("select m from MemberEntity as m where m.account=?1") // ?1은 매개변수의 첫번째 파라미터를 의미한다. =:은 이름으로
    MemberEntity getMemberByAccount(String account);


    //닉네임과 성별 동시만족 조건으로 회원조회
    @Query("select m from MemberEntity m where m.nickName=:nick and m.gender=:gen")  //필드명을써줘야함 m.user_nick 은 X
    List<MemberEntity> getMembersByNickAndGender(String nick, Gender gen);

    // 닉네임과 성별 동시만족 조건으로 회원 조회


    //orderBy
    @Query("select m from MemberEntity m where m.nickName like %:nick%")
    List<MemberEntity> getMemberByNickName(@Param("nick")String nick);


    //delete
    @Transactional
    @Modifying      // 수정, 삭제할 때 붙이기
    @Query("delete from MemberEntity m where m.nickName=:nick")
    void deleteByNickName(@Param("nick")String nick);
}
