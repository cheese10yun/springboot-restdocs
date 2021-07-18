package com.rest.docs.springbootrestdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.docs.springbootrestdocs.member.MemberStatus;
import java.util.EnumSet;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RestDocumentApi {

    public RestDocumentApi(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private final ObjectMapper mapper;

    @PostMapping("/request")
    public void asd(@RequestBody @Valid SampleRequest dto) {

    }

    @GetMapping("/MemberStatus")
    public ArrayNode getBankCorpTypes() {
        final ArrayNode arrayNode = mapper.createArrayNode();
        final EnumSet<MemberStatus> types = EnumSet.allOf(MemberStatus.class);
        for (final MemberStatus type : types) {
            final ObjectNode node = mapper.createObjectNode();
            node.put("MemberStatus", type.name());
            node.put("description", type.getDescription());
            arrayNode.add(node);
        }
        return arrayNode;
    }

    public static class SampleRequest {

        @NotEmpty
        private String name;
        @Email
        private String email;

        public SampleRequest(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

    }
}


