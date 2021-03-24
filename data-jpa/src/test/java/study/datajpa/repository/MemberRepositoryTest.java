package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//SPRING DATA JPA
@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

//        Optional<Member> byId = memberRepository.findById(savedMember.getId());//반환 타입이 옵셔널로 제공되는데 있을수도 있고 없을 수도 있기 때문이다.
//        Member member1 = byId.get();
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void basisCRUD(){
        Member member1 =  new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();;
        assertThat(count).isEqualTo(2);

        //삭제검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        //카운트검증
        long deletedCount = memberRepository.count();;
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");

        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }


    @Test
    public void testQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA",10);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findUsernameList(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA",10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> usernameList = memberRepository.findByNames(Arrays.asList("AAA","BBB"));
        for (Member member : usernameList) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType(){
        Member m1 = new Member("AAA",10);
        memberRepository.save(m1);

        List<Member> aaa = memberRepository.findListByUsername("AAA");
        System.out.println("aaa = " + aaa);
        Optional<Member> aa2 = memberRepository.findOptionalByUsername("AAA");
        System.out.println("aa2 = " + aa2);

        //다건일경우 무조건 null이 아님을 보장한다. ->없는것을 찾아도 empty를 반환
        List<Member> result = memberRepository.findListByUsername("asdasdffs");
        System.out.println("result = " + result.size());

        //다건일경우 null 을 반환 -> exceptio터진것을 자체적으로 ,try catch를 감싸서 null을 반환해줌.
        Member findMember = memberRepository.findMemberByUsername("asdasdffs");
        System.out.println("findMember = " + findMember);

        //자바 8인 경우 니가 data있을지 없을지 모르면 optional타입으로 반환을 해!
        Optional<Member> resultOptional = memberRepository.findOptionalByUsername("asdasdffs");
        System.out.println("resultOptional = " + resultOptional);
        //        Member m1 = new Member("AAA",10);
        //        Member m1 = new Member("AAA",20); 반환이 두개인경우 optinal로 하면
        //만약 조횐하는 결과가 두개이상일경우 예외가 발생한다.


    }


    @Test
    public void paging(){
        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age =10; //Spring data jpa는 paging을 0부터 시작한다.
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        //0page에서 3개 가져와 sorting 조건은 username DESC
        //sorting 안할려면 안써도된다.

        //when
        //
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        //long totalCount = memberRepository.totalCount(age);
        //total count도 지워도된다. 반환 타입을 page로 받으면 totalCount 가 필요하니까라고 하면서  totalCount쿼리까지 같이 날린다.
        List<Member> content = page.getContent();
        //long totalElements = page.getTotalElements();

        page.map(member -> new MemberDto(member.getId(),member.getUsername(),null));//내부의 것을 바꿔서 다른결과를 내는 것

        //then
        assertThat(content.size()).isEqualTo(3);// 현재 페이지 글 수
        assertThat(page.getTotalElements()).isEqualTo(5);// 전체 수
        assertThat(page.getNumber()).isEqualTo(0); //현재 페이지
        assertThat(page.getTotalPages()).isEqualTo(2);// 전체 페이지수
        assertThat(page.isFirst()).isTrue(); //첫페이지인가?
        assertThat(page.hasNext()).isTrue(); //다음페이지가 있는가?

        //when
//        Slice<Member> page = memberRepository.findByAge(age, pageRequest); //반환타입만으로도 page 및 slice로 바꿀 수 있다.
//
//
//
//
//
//
//
//        //then
//        List<Member> content = page.getContent();
//
//
//        assertThat(content.size()).isEqualTo(3); //현재 페이지 글 수
//      //  assertThat(page.getTotalElements()).isEqualTo(5);// 전체 수
//        assertThat(page.getNumber()).isEqualTo(0); //현재 페이지
//      //  assertThat(page.getTotalPages()).isEqualTo(2); 전체 페이지수
//        assertThat(page.isFirst()).isTrue();// 첫페이지인가?
//        assertThat(page.hasNext()).isTrue(); //다음페이지가 있는가?


       // List<Member> page = memberRepository.findByAge(age, pageRequest);

    }
    @Test
    public void bulkUpdate(){
        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",19));
        memberRepository.save(new Member("member3",20));
        memberRepository.save(new Member("member4",21));
        memberRepository.save(new Member("member5",40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);
       // em.flush(); //남아있는 변경되지 않는 내용 반영
        //em.clear(); //영속성 컨텍스트 초기화

        List<Member> member5 = memberRepository.findListByUsername("member5");
        for (Member member : member5) {
            System.out.println("member = " + member);
        }

        //then
        assertThat(resultCount).isEqualTo(3);
    }


    @Test
    public void findMemberLazy(){
        //given
        //member 1 - > teamA
        //member 2 - > teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",10,teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        
        em.flush();
        em.clear();
        
        //when
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

    }
}
