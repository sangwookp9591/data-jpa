package study.datajpa.dto;


import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //DTO는 Entity를 봐도된다. 이렇게도 사용해도된다.
    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();

    }
}
