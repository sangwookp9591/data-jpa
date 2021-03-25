package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired private ItemRepository itemRepository;

    @Test
    public void save(){
        Item item = new Item("A"); //임의의 A를 PK로 잡았을 경우
        itemRepository.save(item);
        //너 새거아니야

        //식별자가 객체일 경우는 NULL 여부로 판단하는데 NULL이 아니고 값이 있기때문에 merge로 간다.
        //그럼 merge로 갓으면 ? merge는 DB에 있을거라고 가정하고 동작하기때문에 우선 DB에서 A를 찾는다 A를 찾았는데
        //A가 없으면 새거구나라고 판단하고 다시 INSERT를 날린다.
        //비효율적이다 쿼리가 한번더 나가고 데이터를 갈아끼우는 것이기떄문에.

    }

}