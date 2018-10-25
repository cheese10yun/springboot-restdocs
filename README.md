[![Build Status](https://travis-ci.org/cheese10yun/springboot-restdocs.svg?branch=master)](https://travis-ci.org/cheese10yun/springboot-restdocs)
[![Coverage Status](https://coveralls.io/repos/github/cheese10yun/springboot-restdocs/badge.svg)](https://coveralls.io/github/cheese10yun/springboot-restdocs)

## REST Docs 소개

Spring REST Docs는 테스트 코드 기반으로 RESTful 문서생성을 돕는 도구로 기본적으로 Asciidoctor를 사용하여 HTML를 생성합니다. Spring MVC 테스트 프레임워크로 생성된 snippet을 사용해서 snippet이 올바르지 않으면 생성된 테스트가 실패하여 정확성을 보장해줍니다.

## Rest Docs vs Swagger
우선 Rest Docs과 Swagger는 성격이 많이 다르다고 생각합니다. Swagger는 RESTful 문서에 대한 명세 보다는 Postman Tool 처럼 특정 API를 쉽게 호출해볼수 있는 것에 초점이 맞춰져있다고 생각합니다. 다시 말해 Swagger는 API 명세에 대한 기능은 어느정도 제공해주지만 그것은 효율적이지 못하다고 생각합니다. 

```java
@ApiOperation(value = "View a list of available products", response = Iterable.class)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
public Iterable list(Model model){
    Iterable productList = productService.listAllProducts();
    return productList;
}
```
* [코드 출처](https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/)

Swagger는 RESTful에대한 문서명세를 위와같은 어노테이션 방법으로 제공해줍니다. 이런 방법은 다음과 같은 단점이 있다고 생각합니다.

* 실제 코드에 영향을 미치지 않지만 지속해서 추가됨으로써 실제 코드보다 문서 명세에 대한 코드가 더 길어져 전체적인 가독성이 떨어집니다.
* **해당 코드는 주석일뿐 로직에 영향을 미치지 않기 때문에 비즈니스 로직이 변경되더라도 문서를 갱신하지 않아 결국 문서와 코드가 일치하지 않게 됩니다.**
* **정상적인 Reponse에 대한 명세 일뿐 status 2xx 이외의 값에 대한 정의가 힘듭니다. 이것을 정의하더라도 주석 형태로 정의하게 되어 결국 시간이 지나면 문서와 코드가 일치하지 않게 됩니다.**


제 개인적인 결론은 RESTful API에 대한 명세관점에서는 둘은 비교 대상이 되기 힘들다고 생각합니다. **Rest Docs는 테스트 코드 기반으로 문서가 작성되기 때문에 문서와 실제 코드의 일치성이 높고 테스트코드로 문서가 표현되기 때문에 실제 코드에 어떠한 코드 추가도 필요가 없다는 장점이 크다고 생각합니다.** Swaager이외에도 실제 자바 주석문으로 RESTful API를 명세하는 서비스도 있지만 결국 문서와 코드의 일치성 등 다양항문제로 현재로써는 Rest Docs가 가장 효율적인 RESTful API 명세 도구라고 생각합니다.



## pom.xml 설정

```xml
<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.restdocs</groupId>
        <artifactId>spring-restdocs-mockmvc</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<plugins>
    <plugin>
        <groupId>org.asciidoctor</groupId>
        <artifactId>asciidoctor-maven-plugin</artifactId>
        <version>1.5.3</version>
        <executions>
            <execution>
                <id>generate-docs</id>
                <phase>prepare-package</phase>
                <goals>
                    <goal>process-asciidoc</goal>
                </goals>
                <configuration>
                    <backend>html</backend>
                    <doctype>book</doctype>
                </configuration>
            </execution>
        </executions>
        <dependencies>
            <dependency>
                <groupId>org.springframework.restdocs</groupId>
                <artifactId>spring-restdocs-asciidoctor</artifactId>
                <version>2.0.2.RELEASE</version>
            </dependency>
        </dependencies>
    </plugin>

    <plugin>
        <artifactId>maven-resources-plugin</artifactId>
        <version>2.7</version>
        <executions>
            <execution>
                <id>copy-resources</id>
                <phase>prepare-package</phase>
                <goals>
                    <goal>copy-resources</goal>
                </goals>
                <configuration>
                    <outputDirectory>
                        ${project.build.outputDirectory}/static/docs
                    </outputDirectory>
                    <resources>
                        <resource>
                            <directory>
                                ${project.build.directory}/generated-docs
                            </directory>
                        </resource>
                    </resources>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```

## Test Code

### Test Code 설정
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {

    @Rule public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired private WebApplicationContext context;
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
}
```


### Member 조회 Test Code
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {
    ...
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
}
```

<p algin ="cneter">
    <img src ="/assets/target.png">
</p>