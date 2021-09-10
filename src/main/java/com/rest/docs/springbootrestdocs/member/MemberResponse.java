package com.rest.docs.springbootrestdocs.member;


import lombok.Getter;

@Getter
public class MemberResponse {
    private final Long id;
    private final String nickname;
    private final String email;

    public MemberResponse(final Member member) {
        this.id = member.getId();
        this.nickname = member.getName();
        this.email = member.getEmail();
    }
}
