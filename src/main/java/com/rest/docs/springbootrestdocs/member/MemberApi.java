package com.rest.docs.springbootrestdocs.member;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApi {

    @GetMapping("/restdocs")
    public List<Member> getMember1(
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        final List<Member> members = new ArrayList<>();
        members.add(new Member("yun@asd.com", "yun"));
        return members;
    }
}