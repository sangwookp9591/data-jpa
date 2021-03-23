package study.datajpa.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id","name"})
public class Team {

    @Id
    @GeneratedValue
    @Column(name ="team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")//FK 가 없는쪽에
    private List<Member> members =  new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
