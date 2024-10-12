package baekgwa.itemauction.web.mypage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import baekgwa.itemauction.IntegrationControllerTest;
import baekgwa.itemauction.annotation.WithCustomUser;
import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.web.mypage.MyPageForm.ProfileInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class MyPageControllerTest extends IntegrationControllerTest {

    @DisplayName("[Success] 회원은 자신의 마이페이지에 접근할 수 있습니다.")
    @Test
    @WithCustomUser
    void myPage() throws Exception {
        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/mypage"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("myPage/myPageHome"));
    }

    @DisplayName("[Success] 로그인 되지 않은 사용자가 마이페이지에 접근하려고 하면, 로그인 페이지로 Redirect 됩니다.")
    @Test
    void myPageNotUser() throws Exception {
        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/mypage"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("[Success] 내 사용자 정보를 조회합니다. 로그인된 사용자만 이용할 수 있습니다.")
    @WithCustomUser
    @Test
    void myProfileForm() throws Exception {
        // given
        MyPageForm.ProfileInfo profileInfo =
                ProfileInfo
                        .builder()
                        .nickName("백과")
                        .name("강성욱")
                        .email("test@test.com")
                        .phone("01011112222")
                        .userGrade(UserGrade.BRONZE.getText())
                        .build();

        // stubbing
        BDDMockito.given(userProfileService.findUserDataMyPageForm(null))
                .willReturn(profileInfo);

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/profiles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("myPage/profile"))
                .andExpect(model().attributeExists("profileForm"))
                .andExpect(result -> {
                            MyPageForm.ProfileInfo data = (ProfileInfo) result.getModelAndView().getModel()
                                    .get("profileForm");
                            assertThat(data)
                                    .extracting("nickName", "name", "email", "phone", "userGrade")
                                    .contains("백과", "강성욱", "test@test.com", "01011112222",
                                            UserGrade.BRONZE.getText());
                        }
                );
    }

    @DisplayName("[Success] 로그인 안된 사용자가 접근하면 로그인 페이지로 리다이렉트 됩니다.")
    @Test
    void myProfileFormNotUser() throws Exception {
        // given

        // stubbing

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get("/mypage/profiles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("[Success] 회원 데이터를 받아서 Profile 정보를 업데이트 합니다.")
    @WithCustomUser
    @Test
    void editProfile() throws Exception {
        // given

        // stubbing

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/mypage/profiles")
                        .param("nickName", "백과")
                        .param("name", "강성욱")
                        .param("email", "test@test.com")
                        .param("phone", "01011112222")
                        .param("userGrade", UserGrade.BRONZE.getText())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mypage"));
    }

    @DisplayName("[Success] 로그인하지 않은 회원이 접근하면, 요청을 차단하고, 로그인 페이지로 리다이렉트 시킵니다.")
    @Test
    void editProfileNotUser() throws Exception {
        // given

        // stubbing

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/mypage/profiles")
                        .param("nickName", "백과")
                        .param("name", "강성욱")
                        .param("email", "test@test.com")
                        .param("phone", "01011112222")
                        .param("userGrade", UserGrade.BRONZE.getText())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("[Success] 입력된 결과가 조건에 맞지 않으면, 요청을 보류하고 다시 화면을 표시합니다.")
    @Test
    @WithCustomUser
    void editProfileValidateError() throws Exception {
        // given

        // stubbing

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/mypage/profiles")
                        .param("nickName", "")
                        .param("name", "")
                        .param("email", "")
                        .param("phone", "")
                        .param("userGrade", UserGrade.BRONZE.getText())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("myPage/profile"));
    }
}