package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> { //JpaRepository<type,id(pk type)>

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //@Query(name="Member.findByUsername") 없애도 잘동작함 ? why ->
    //JpaRepository type에 있는 Entity명에다가 점을찍고 메서드명을 붙여서 먼저 찾는다. named 쿼리가 있으면 실행하고 메서드쿼리를 찾는다.
    List<Member> findByUsername(@Param("username") String username);
}
