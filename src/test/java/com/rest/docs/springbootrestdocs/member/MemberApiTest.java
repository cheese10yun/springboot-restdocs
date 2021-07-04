package com.rest.docs.springbootrestdocs.member;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
    public void member_조회_테스트() throws Exception {
        mockMvc.perform(
            get("/api/members/restdocs")
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
                    ),
                    responseFields(
                        fieldWithPath("[0].id").description("PK"),
                        fieldWithPath("[0].email").description("Email"),
                        fieldWithPath("[0].name").description("Name"),
                        fieldWithPath("[0].createdAt").description("생성 일시"),
                        fieldWithPath("[0].updatedAt").description("수정 일시")
                    )
                )
            )
            .andExpect(status().isOk())
        ;
    }
}