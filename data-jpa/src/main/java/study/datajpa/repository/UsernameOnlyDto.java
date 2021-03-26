package study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) { //생성자에 파라미터 이름으로 매칭시켜서 프로젝션도 된다.
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
