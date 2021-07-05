package com.rest.docs.springbootrestdocs.member;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberApi {
    private final MemberRepository memberRepository;

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("NotFound"));
    }

    @GetMapping
    public Page<Member> getMembers(
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
    ) {
        return memberRepository.findAll(pageable);
    }

    @PostMapping
    public void registerMember(@RequestBody @Valid MemberRegistrationRequest dto) {
        memberRepository.save(new Member(dto.getEmail(), dto.getName()));
    }
}