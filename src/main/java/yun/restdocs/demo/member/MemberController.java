package yun.restdocs.demo.member;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import yun.restdocs.demo.member.dto.MemberResponseDto;
import yun.restdocs.demo.member.dto.SignUpDto;

import javax.validation.Valid;

@RestController
@RequestMapping("members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponseDto signUp(@RequestBody @Valid SignUpDto dto) {
        return new MemberResponseDto(memberService.signUp(dto));
    }

    @GetMapping(value = "/{id}")
    public MemberResponseDto getUser(@PathVariable Long id) {
        return new MemberResponseDto(memberService.findById(id));
    }

    @GetMapping
    public PageImpl<MemberResponseDto> getUsers(Pageable pageable) {
        return memberService.findAll(pageable);
    }

}
