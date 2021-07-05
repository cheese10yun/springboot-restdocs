package com.rest.docs.springbootrestdocs.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRegistrationRequest {

    @NotEmpty
    private final String name;
    @Email
    private final String email;
}
