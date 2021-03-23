package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEnitty() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 =  new Member("member1",10,teamA);
        Member member2 =  new Member("member2",20,teamA);
        Member member3 =  new Member("member3",30,teamB);
        Member member4 =  new Member("member4",40,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기회
        em.flush();//영속성컨텍스트에 모아논것을 강제로 db에 insert query를 날림
        em.clear();//DB에 쿼리를 날리고 영속성컨텍스트 캐시를 다날려리기떄문에 깔끔하게 조회가능

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println("member = "+member);
            System.out.println("member.tema = "+member.getTeam());
        }

    }
}