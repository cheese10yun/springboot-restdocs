package com.rest.docs.springbootrestdocs.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegistrationRequest {

    @NotEmpty
    private String name;
    @Email
    private String email;
}