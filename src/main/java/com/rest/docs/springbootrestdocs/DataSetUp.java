package com.rest.docs.springbootrestdocs;

import com.rest.docs.springbootrestdocs.member.Member;
import com.rest.docs.springbootrestdocs.member.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSetUp implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) {
        final List<Member> members = new ArrayList<>();

        members.add(new Member("yun@asd.com", "yun"));
        members.add(new Member("wan@asd.com", "wan"));
        members.add(new Member("jay@asd.com", "jay"));
        members.add(new Member("joo@asd.com", "joo"));

        memberRepository.saveAll(members);
    }
}
