package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

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
