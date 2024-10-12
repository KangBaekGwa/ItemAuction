package baekgwa.itemauction.web.user;

import static org.mockito.ArgumentMatchers.anyString;

import baekgwa.itemauction.IntegrationControllerTest;
import baekgwa.itemauction.web.user.UserResponse.CheckDuplicateLoginId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
                .andExpect(MockMvcResultMatchers.view().name("user/addUserForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newMemberForm"));
    }

    @DisplayName("[Success] 회원가입을 진행 합니다.")
    @Test
    void addNewUserProc() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("loginId", "test1")
                        .param("password", "!1234asdf")
                        .param("name", "강성욱")
                        .param("nickName", "백과님")
                        .param("email", "test@test.com")
                        .param("phone", "01012341234")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));
    }

    @DisplayName("[Success] 회원가입입 중, 유효성 검증에 실패하면 회원가입되지 않고 페이지를 유지합니다.")
    @Test
    void addNewUserProcFail() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("loginId", "t")
                        .param("password", "123")
                        .param("name", "!!@@")
                        .param("nickName", "!!@#@")
                        .param("email", "noneEmail")
                        .param("phone", "nonePhone")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/addUserForm"));
    }

    @DisplayName("[Success] 회원가입 시, 로그인 아이디의 중복 확인을 진행 합니다.")
    @Test
    void checkDuplicateLoginId() throws Exception {
        // given

        // stubbing
        CheckDuplicateLoginId response = CheckDuplicateLoginId.builder().duplicate(Boolean.FALSE).build();
        BDDMockito.given(userService.checkDuplicateLoginId(anyString())).willReturn(response);

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add/check")
                        .param("loginId", "loginId"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("duplicate").value(response.isDuplicate()));
    }
}