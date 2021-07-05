package com.rest.docs.springbootrestdocs.member;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
class MemberApiTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(
        WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .build();
    }

    @Test
    public void restdocs_snippets_확인() throws Exception {
        mockMvc.perform(
            get("/api/members/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk())
        ;

    }

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
            get("/api/members/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    pathParameters(
                        parameterWithName("id").description("Member's id")
                    ),
                    responseFields(
                        fieldWithPath("id").description("PK"),
                        fieldWithPath("email").description("Email"),
                        fieldWithPath("name").description("Name"),
                        fieldWithPath("createdAt").description("생성 일시"),
                        fieldWithPath("updatedAt").description("수정 일시")
                    )
                )
            )
            .andExpect(status().isOk())
        ;

    }

    @Test
    public void member_page() throws Exception {
        mockMvc.perform(
            get("/api/members")
                .param("size", "10")
                .param("page", "0")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    requestParameters(
                        parameterWithName("size").optional().description("size"),
                        parameterWithName("page").optional().description("page")
                    )
                )
            )
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void member_등록() throws Exception {
        mockMvc.perform(
            post("/api/members")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"yun\", \"email\": \"writer@asd.com\"}")
        )
            .andDo(print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    requestFields(
                        fieldWithPath("name").description("name"),
                        fieldWithPath("email").description("email")
                    )
                )
            )
        ;
    }
}