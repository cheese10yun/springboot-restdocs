package yun.restdocs.demo.member.dto;

import lombok.Getter;
import yun.restdocs.demo.member.Member;
import yun.restdocs.demo.model.Address;
import yun.restdocs.demo.model.Email;

@Getter
public class MemberResponseDto {
    private Email email;
    private Address address;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.address = member.getAddress();
    }

}
