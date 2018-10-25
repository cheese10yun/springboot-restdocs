package yun.restdocs.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import yun.restdocs.demo.member.Member;
import yun.restdocs.demo.member.MemberRepository;
import yun.restdocs.demo.model.Address;
import yun.restdocs.demo.model.Email;

import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class DataRunner implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        IntStream.range(0, 10).forEach(i -> {

            final Member member = Member.builder()
                    .address(buildAddress(i))
                    .email(buildEmail(i))
                    .build();

            memberRepository.save(member);
        });


    }

    private Email buildEmail(int i) {
        return Email.builder()
                .value(String.format("test%d@test.com", i))
                .build();
    }

    private Address buildAddress(int i) {
        return Address.builder()
                .city("city" + i)
                .street("street" + i)
                .zipCode("zipCode" + i)
                .build();
    }
}
