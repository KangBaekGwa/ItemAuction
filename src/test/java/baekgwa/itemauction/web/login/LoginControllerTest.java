package baekgwa.itemauction.web.login;

import static org.junit.jupiter.api.Assertions.*;

import baekgwa.itemauction.IntegrationControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class LoginControllerTest extends IntegrationControllerTest {

    @DisplayName("[Success] 로그인 폼 페이지를 보여줍니다.")
    @Test
    void loginForm() throws Exception {
        // given

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login/loginForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("loginForm"));
    }
}