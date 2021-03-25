package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //아이디가 pk가 들어간거라서 domain convertor라는 기숧을 쓸수 있다.
    //spring boot가 중간의 Member member = memberRepository.findById(id).get(); 컨버팅하는 과정을 다끝내고 인젝션해준것이다.

    //이기능을 권장하지않은 pk가 외부에 공개되어 조회하는 경우는 드물고 쿼리 조회가 복잡해지면 쓸수 없다.
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 12, sort = "username",
            direction = Sort.Direction.DESC) Pageable pageable){ //Page 결과 정보 Pagable 파라미터 정보
//        Page<Member> page = memberRepository.findAll(pageable);
//
//        //entity를 외부에 노출하는 것은 내부설계를 밖에 노출하는 것이다. 규약에 의한게 의미가 없어지고, API 스팩이 바뀐다.
//        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
//        return map;

        //entity를 외부에 노출하는 것은 내부설계를 밖에 노출하는 것이다. 규약에 의한게 의미가 없어지고, API 스팩이 바뀐다.
//        return memberRepository.findAll(pageable)
//                .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
//
 //       PageRequest request = PageRequest.of(1, 2);
//        Page<MemberDto> map = memberRepository.findAll(request)
//                .map(MemberDto::new);
//        MyPage<MemberDto>
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

    //@PostConstruct
    public void init(){
        for (int i = 0 ; i< 100 ; i++){
            memberRepository.save(new Member("user"+i,i));
        }
    }


}
