package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> { //JpaRepository<type,id(pk type)>

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //@Query(name="Member.findByUsername") 없애도 잘동작함 ? why ->
    //JpaRepository type에 있는 Entity명에다가 점을찍고 메서드명을 붙여서 먼저 찾는다. named 쿼리가 있으면 실행하고 메서드쿼리를 찾는다.
    List<Member> findByUsername(@Param("username") String username);

    // 복잡한 jpql을 해결
    @Query("select m from Member m where m.username =:username and m.age =:age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);


    @Query("select m.username from Member m")
    List<String> findUsernameList();


    //new Operation으로 생성
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();


    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

}
