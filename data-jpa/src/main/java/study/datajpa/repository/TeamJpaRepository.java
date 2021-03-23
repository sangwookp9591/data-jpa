package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext //jpa em을 인젝션해주는 어노테이션
    private EntityManager em;

    public Team save(Team team){
         em.persist(team);
        return team;
    }

    public void delete(Team team){
        em.remove(team);
    }

    public List<Team> findAll(){
        List<Team> teams = em.createQuery("select t from Team t", Team.class).getResultList();
        return teams;
    }

    public Optional<Team> findById(Long id){
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }
    public long count(){
        return em.createQuery("select count(t) from Team t",Long.class).getSingleResult();
    }

}
