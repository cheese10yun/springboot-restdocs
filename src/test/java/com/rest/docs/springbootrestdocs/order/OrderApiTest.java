package com.rest.docs.springbootrestdocs.order;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rest.docs.springbootrestdocs.SpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class OrderApiTest extends SpringTestSupport {

    @Test
    public void order_get() throws Exception {
        mockMvc.perform(
            get("/api/orders/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
        ;
    }
}