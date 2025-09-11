package entus.authServer.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("PublicMethod")
    void PublicMethod() throws Exception {
        //public
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("public"));
        //user
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
        //admin
        mockMvc.perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("UserMethod")
    void UserMethod() throws Exception {
        //public
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("public"));
        //user
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
        //admin
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("AdminMethod")
    void AdminMethod() throws Exception {
        //public
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("public"));
        //user
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
        //admin
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin"));
    }
}