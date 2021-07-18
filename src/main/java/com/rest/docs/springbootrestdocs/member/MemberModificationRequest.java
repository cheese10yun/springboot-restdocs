package com.rest.docs.springbootrestdocs.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberModificationRequest {

    private final MemberStatus status;
}
