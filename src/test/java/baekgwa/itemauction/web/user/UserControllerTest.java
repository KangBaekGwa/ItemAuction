package baekgwa.itemauction.web.user;

import baekgwa.itemauction.IntegrationControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UserControllerTest extends IntegrationControllerTest {

    @DisplayName("[Success] 회원가입 창으로 연결됩니다.")
    @Test
    void addNewUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/user/addUserForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newMemberForm"));
    }

    @DisplayName("[Success] 회원가입을 진행 합니다.")
    @Test
    void addNewUserProc() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));
    }
}