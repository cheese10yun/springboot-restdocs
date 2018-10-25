package yun.restdocs.demo.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yun.restdocs.demo.model.Address;
import yun.restdocs.demo.model.Email;

import javax.persistence.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Email email;

    @Embedded
    private Address address;

    @Builder
    public Member(Email email, Address address) {
        this.email = email;
        this.address = address;
    }
}
