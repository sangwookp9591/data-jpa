package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
    //spring data jpa가 아니라 직접 구현한 기능을 쓰고싶은 경우.
    //구현은 다른 클래스에서했지만 MemberRepository에서 동작을 시키면 다른곳에 구현되있는것을 탄다.
    // 이유는 Spring Data JPA가 연계해 주기 때문이다.

    //QueryDSL을 쓸대 커스텀해서 많이 사용.
    //간단한건 SPRING DATA JPA를쓰고 복잡한것은 QueryDSL을 쓴다



}
