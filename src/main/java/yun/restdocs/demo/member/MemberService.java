package yun.restdocs.demo.member;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.restdocs.demo.member.dto.MemberResponseDto;
import yun.restdocs.demo.member.dto.SignUpDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public Member signUp(SignUpDto dto) {
        final Member member = dto.toEntity();
        return memberRepository.save(member);
    }

    public Member findById(long id) {
        final Optional<Member> member = memberRepository.findById(id);
        return member.get();
    }

    public PageImpl<MemberResponseDto> findAll(Pageable pageable) {
        final Page<Member> page = memberRepository.findAll(pageable);
        final List<MemberResponseDto> content = convert(page);
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    private List<MemberResponseDto> convert(Page<Member> members) {
        return members.getContent()
                .parallelStream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }
}
