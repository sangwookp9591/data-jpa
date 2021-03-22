package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    //jpa이기때문에 기본생성자가 필요하다. Entity를 만들떄 기본적으로 파라미터가 없는 기본생성자가 필요하다.
    // 그리고 access level은 private에서 멈추면안되고 protected까지 열어놔야한다.
    // 이유는 jpa가 프록시 기술을 쓰는데 jpa구현체들이 객체를 강제로 만들어야하는데. private으로하면 그게 안되기때문에
   protected Member() {

    }
    public Member(String username) {
        this.username = username;
    }

}
