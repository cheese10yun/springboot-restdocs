package yun.restdocs.demo.member;

import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Before
    public void setUp() {

        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document)
                .build();
    }

    @Test
    public void get_member() throws Exception {
        mockMvc.perform(get("/members/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("id").description("Member's id")
                        ),
                        responseFields(
                                fieldWithPath("email.value").description("The Member's email address"),
                                fieldWithPath("address.city").description("The Member's address city"),
                                fieldWithPath("address.street").description("The Member's address street"),
                                fieldWithPath("address.zipCode").description("The Member's address zipCode")
                        )
                ))
                .andExpect(jsonPath("$.email.value", is(notNullValue())))
                .andExpect(jsonPath("$.address.city", is(notNullValue())))
                .andExpect(jsonPath("$.address.street", is(notNullValue())))
                .andExpect(jsonPath("$.address.zipCode", is(notNullValue())))
        ;

    }

    @Test
    public void get_members() throws Exception {
        mockMvc.perform(get("/members?size=5&page=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("size").description("number of list"),
                                parameterWithName("page").description("page")
                        ),
                        responseFields(
                                fieldWithPath("content.[].email.value").description("list"),
                                fieldWithPath("content.[].address.city").description("list"),
                                fieldWithPath("content.[].address.street").description("list"),
                                fieldWithPath("content.[].address.zipCode").description("list"),
                                fieldWithPath("pageable.sort.sorted").description(".."),
                                fieldWithPath("pageable.sort.unsorted").description(".."),
                                fieldWithPath("pageable.sort.empty").description(".."),
                                fieldWithPath("pageable.offset").description(".."),
                                fieldWithPath("pageable.pageSize").description(".."),
                                fieldWithPath("pageable.pageNumber").description(".."),
                                fieldWithPath("pageable.paged").description(".."),
                                fieldWithPath("pageable.unpaged").description(".."),
                                fieldWithPath("last").description(".."),
                                fieldWithPath("totalPages").description(".."),
                                fieldWithPath("totalElements").description(".."),
                                fieldWithPath("size").description(".."),
                                fieldWithPath("number").description(".."),
                                fieldWithPath("first").description(".."),
                                fieldWithPath("numberOfElements").description(".."),
                                fieldWithPath("sort.sorted").description(".."),
                                fieldWithPath("sort.unsorted").description(".."),
                                fieldWithPath("sort.empty").description(".."),
                                fieldWithPath("empty").description("..")
                        )
                ));

    }

    @Test
    public void sign_up() throws Exception {
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signUpRequestBody(
                        RandomString.make(8) + "@test.com",
                        "city",
                        "street",
                        "zipCode")))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void sign_up_fail() throws Exception {
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signUpRequestBody(
                        RandomString.make(8) + "test.com",
                        "city",
                        "street",
                        "zipCode")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private String signUpRequestBody(String email, String city, String street, String zipCode) {

        return "{\n" +
                "  \"email\" : {\n" +
                "    \"value\" : \"" + email + "\"\n" +
                "  },\n" +
                "  \"address\" : {\n" +
                "    \"city\" : \"" + city + "\",\n" +
                "    \"street\" : \"" + street + "\",\n" +
                "    \"zipCode\" : \"" + zipCode + "\"\n" +
                "  }\n" +
                "}";

    }
}