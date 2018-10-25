[![Build Status](https://travis-ci.org/cheese10yun/springboot-restdocs.svg?branch=master)](https://travis-ci.org/cheese10yun/springboot-restdocs)
[![Coverage Status](https://coveralls.io/repos/github/cheese10yun/springboot-restdocs/badge.svg)](https://coveralls.io/github/cheese10yun/springboot-restdocs)

## REST Docs 소개
... 작업중...

## Rest Docs vs Swagger

## Rest Docs 장점


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