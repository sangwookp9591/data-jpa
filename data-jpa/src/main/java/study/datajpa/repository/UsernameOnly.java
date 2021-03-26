package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    @Value("#{target.username + ' '+target.age }") //open projection - spl을 쓰며 멤버를 다가져온다 거기에서 spl을 계산함
        // 둘다가져와서 문자로 더해서 넣어줌 spring spl 문법
    String getUsername(); //close projection 정확하게 매칭 select 절이 최적회됨
}
