package com.guilherme.price_tracker.product;

import com.guilherme.price_tracker.user.User;
import com.guilherme.price_tracker.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registersProductAndExtractsExternalId() throws Exception {
        User user = userRepository.save(User.builder()
                .name("Test User")
                .email("test-" + UUID.randomUUID() + "@example.com")
                .password("irrelevant")
                .build());

        Map<String, Object> body = Map.of(
                "url", "https://produto.mercadolivre.com.br/MLB-123456789-some-product",
                "targetPrice", new BigDecimal("199.90"),
                "userId", user.getId()
        );

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.externalId").value("MLB123456789"))
                .andExpect(jsonPath("$.targetPrice").value(199.90))
                .andExpect(jsonPath("$.ownerId").value(user.getId().toString()));
    }

    @Test
    void rejectsUrlWithoutExtractableItemId() throws Exception {
        User user = userRepository.save(User.builder()
                .name("Test User")
                .email("test-" + UUID.randomUUID() + "@example.com")
                .password("irrelevant")
                .build());

        Map<String, Object> body = Map.of(
                "url", "https://example.com/not-a-mercado-livre-url",
                "targetPrice", new BigDecimal("10.00"),
                "userId", user.getId()
        );

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsUnknownUser() throws Exception {
        Map<String, Object> body = Map.of(
                "url", "https://produto.mercadolivre.com.br/MLB-123456789-some-product",
                "targetPrice", new BigDecimal("10.00"),
                "userId", UUID.randomUUID()
        );

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }
}
