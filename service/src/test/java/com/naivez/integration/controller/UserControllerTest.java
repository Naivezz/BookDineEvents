package com.naivez.integration.controller;

import com.naivez.entity.enums.Role;
import com.naivez.integration.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private static final long EXISTING_USER_ID = 1L;
    private static final long EXISTING_USER_ID_TO_DELETE = 2L;
    private static final long NON_EXISTING_USER_ID = 100L;
    private static final String ADMIN_EMAIL = "jane.smith@example.com";
    private static final String USER_EMAIL = "john.doe@example.com";


    @Test
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(4)));
    }

    @Test
    void shouldReturnUserById_whenUserExists() throws Exception {
        mockMvc.perform(get("/users/{id}", EXISTING_USER_ID)
                        .with(user(USER_EMAIL).authorities(Role.USER)))
                .andExpect(status().isOk())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/{id}", NON_EXISTING_USER_ID)
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewUserSuccessfully() throws Exception {
        mockMvc.perform(post("/users")
                        .param("firstName", "Test firstname")
                        .param("lastName", "Test lastname")
                        .param("email", "test@gmail.com")
                        .param("password", "aasf32")
                        .param("phoneNumber", "+342942949")
                        .param("role", Role.USER.name())
                        .param("isBlacklisted", "false")
                        .param("blacklistReason", "")
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    void shouldUpdateUserSuccessfully_whenUserExists() throws Exception {
        mockMvc.perform(post("/users/{id}/update", EXISTING_USER_ID)
                        .param("firstName", "Updated firstname")
                        .param("lastName", "Updated lastname")
                        .param("email", "updated@gmail.com")
                        .param("password", "f23f242f24")
                        .param("phoneNumber", "+342828424")
                        .param("role", Role.ADMIN.name())
                        .param("isBlacklisted", "true")
                        .param("blacklistReason", "Bad behavior")
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/" + EXISTING_USER_ID));
    }

    @Test
    void shouldReturnNotFound_whenUpdatingNonExistingUser() throws Exception {
        mockMvc.perform(post("/users/{id}/update", NON_EXISTING_USER_ID)
                        .param("firstName", "Updated firstname")
                        .param("lastName", "Updated lastname")
                        .param("email", "updated@gmail.com")
                        .param("password", "f23f242f24")
                        .param("phoneNumber", "+342828424")
                        .param("role", Role.ADMIN.name())
                        .param("isBlacklisted", "true")
                        .param("blacklistReason", "Bad behavior")
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUserSuccessfully_whenUserExists() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", EXISTING_USER_ID_TO_DELETE)
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void shouldReturnNotFound_whenDeletingNonExistingUser() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", NON_EXISTING_USER_ID)
                        .with(user(ADMIN_EMAIL).authorities(Role.ADMIN)))
                .andExpect(status().isNotFound());
    }
}
