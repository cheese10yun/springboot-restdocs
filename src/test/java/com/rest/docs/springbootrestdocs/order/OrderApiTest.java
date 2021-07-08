package com.rest.docs.springbootrestdocs.order;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rest.docs.springbootrestdocs.SpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

class OrderApiTest extends SpringTestSupport {

    @Test
    public void order_get() throws Exception {
        mockMvc.perform(
            get("/api/orders/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk())
        ;
    }
}