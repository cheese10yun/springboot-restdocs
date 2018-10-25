package yun.restdocs.demo.member.dto;

import lombok.Builder;
import lombok.Getter;
import yun.restdocs.demo.member.Member;
import yun.restdocs.demo.model.Address;
import yun.restdocs.demo.model.Email;

@Getter
public class MemberResponseDto {
    private Email email;
    private Address address;

    @Builder
    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.address = member.getAddress();
    }

}
