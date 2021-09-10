package com.rest.docs.springbootrestdocs.member;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rest.docs.springbootrestdocs.RestDocsConfiguration;
import com.rest.docs.springbootrestdocs.SpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberApiTest extends SpringTestSupport {

    @Test
    public void restdocs_snippets_확인() throws Exception {
        mockMvc.perform(
            get("/api/members/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
            get("/api/members/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("Member id")
                    ),
                    responseFields(
                        fieldWithPath("id").description("Member id"),
                        fieldWithPath("email").description("Email"),
                        fieldWithPath("nickname").description("Name")
                    )
                )
            )
        ;

    }

    @Test
    public void member_page() throws Exception {
        mockMvc.perform(
            get("/api/members")
                .param("size", "10")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestParameters(
                        parameterWithName("size").optional().description("size"),
                        parameterWithName("page").optional().description("page")
                    )
                )
            )
        ;
    }

    @Test
    public void member_등록() throws Exception {
        mockMvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"yun\", \"email\": \"writer@asd.com\"}")
        )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    //@formatter:off
                    requestFields(
                        fieldWithPath("name").description("name").attributes(RestDocsConfiguration.field("length", "10")),
                        fieldWithPath("email").description("email").attributes(RestDocsConfiguration.field("length", "123"))
                    )
                    //@formatter:on
                )
            )
        ;
    }

    @Test
    public void member_status_변경() throws Exception {
        mockMvc.perform(
            put("/api/members/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"LOCK\"}")
        )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("Member id")
                    ),
                    requestFields(
                        fieldWithPath("status").description("회원 상태")
                    )
                )
            );
    }
}