package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> { //JpaRepository<type,id(pk type)>

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

}
