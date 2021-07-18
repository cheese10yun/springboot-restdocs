package com.rest.docs.springbootrestdocs.member;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberModificationRequest {

    @NotNull
    private MemberStatus status;
}
