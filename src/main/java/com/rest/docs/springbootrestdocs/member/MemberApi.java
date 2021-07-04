package com.rest.docs.springbootrestdocs.member;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApi {

    @GetMapping("/restdocs")
    public List<Member> getMember1(
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        final List<Member> members = new ArrayList<>();
        members.add(new Member("yun@asd.com", "yun"));
        return members;
    }


    @ApiOperation(
        value = "members 조회",
        response = Member.class,
        responseContainer = "List"
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "응답 성공"),
            @ApiResponse(code = 400, message = "유효하지 하지 않은 입력")
        }
    )
    @GetMapping("/swagger")
    public List<Member> getMember2(
        @ApiParam(name = "page", value = "page", defaultValue = "0")
        @RequestParam Integer page,
        @ApiParam(name = "size", value = "size", defaultValue = "10")
        @RequestParam Integer size
    ) {
        final List<Member> members = new ArrayList<>();
        members.add(new Member("yun@asd.com", "yun"));
        return members;
    }
}