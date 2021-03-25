package study.datajpa.repository;

import org.hibernate.metamodel.model.domain.internal.MapMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> ,MemberRepositoryCustom{ //JpaRepository<type,id(pk type)>

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

    //반환타입을 유연하게 쓸수 있다.
    List<Member> findListByUsername(String username);//컬렉션
    Member findMemberByUsername(String username);//단건
    Optional<Member> findOptionalByUsername(String username);//단건 Optional


    @Query(value="select m from Member m left join m.team t"
            ,countQuery = "select count(m.username) from Member m") //쿼리가 복잡해질경우 카운트쿼리도 복잡한 쿼리를 그대로 가져가서 성능이 느려짐
    Page<Member> findByAge(int age, Pageable pageable); // 반환타입이 페이지이고, pagable은 쿼리에 대한 조건이 들어간다.
   // Slice<Member> findByAge(int age, Pageable pageable); //


    @Modifying(clearAutomatically = true)//이게 있어야 jpa executeUpdate()가 실행된다.
    @Query("update Member m set m.age =m.age +1 where m.age >=:age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //override후 entitygraph 설정
    @Override
    @EntityGraph(attributePaths = {"team"}) //jpql을 짜기싫으면
    List<Member> findAll(); //member 와 team 조회

    //JPQL + 엔티티 그래프 //쿼리를 짯는데 패치조인만 추가하고 싶을 경우
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 쿼리에서 특히 편리하다. //회원조회할때 왠만하면 팀을 같이 조회하는 경우
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    @QueryHints(value =@QueryHint(name = "org.hibernate.readOnly",value = "true"))
    Member findReadOnlyByUsername(String username);


    //select for update -  db에 select할떄 다른애들 함부로 건들지마
    @Lock(LockModeType.PESSIMISTIC_WRITE) //Javax.persistent이기때문에 JPA꺼다
    List<Member> findLockByUsername(String name);

    //MemberRepository는 다인터페이스라서 부모까지합쳐서 다구현을 해야한다.
    //커스텀할 기능 하나만 쓰고싶다면?

}
