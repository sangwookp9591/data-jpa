package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    //여기서 naming규칙이있는데 MmeberRepository +Impl이규칙만 맞추면
    //findMmeberCustom을 호출했을때 findMmeberCustom을 만든 구현체를 찾아서 호출해준다ㅓ.

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m").getResultList();
    }
}
