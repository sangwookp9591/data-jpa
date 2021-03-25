package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//그냥쓰게되면 member에 날짜가없다 jpa에서 진짜 상속이있고 속성만 내려쓰는 상속ㄷ관계가 있는데
@MappedSuperclass // 속성을 내려서 테이블에서 같이쓸 수 있게 데이터만 공유하는 애
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) //createdDate는 수정 못하게 하는 것
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist //JPA가 제공하는것 persist하기 전에 제공
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;

    }

    @PreUpdate //업데이트 하기 전에 발생
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
