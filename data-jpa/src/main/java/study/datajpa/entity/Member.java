package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id","username", "age"}) //team은 넣으면 안됨 넣게되면 team의 연관관계까지 타서 출력하기 떄문에
//가급적이면 연관관계 필드는 toString안해주는게 좋다.
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch =  FetchType.LAZY) //Member를 조회할때는 멤버만 딱가지고오는것, 실제값을 볼떄 그떄 쿼리를 가져오는것
    @JoinColumn(name = "team_id") //FK
    private Team team;


    //jpa이기때문에 기본생성자가 필요하다. Entity를 만들떄 기본적으로 파라미터가 없는 기본생성자가 필요하다.
    // 그리고 access level은 private에서 멈추면안되고 protected까지 열어놔야한다.
    // 이유는 jpa가 프록시 기술을 쓰는데 jpa구현체들이 객체를 강제로 만들어야하는데. private으로하면 그게 안되기때문에
//    protected Member() {
//    }
// @NoArgsConstructor(access = AccessLevel.PROTECTED)로 대체
    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team teamA) {
        this.username =username;
        this.age =age;
        if(team!=null){
            changeTeam(team);
        }
    }

    //연관관계 메서트
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

}
