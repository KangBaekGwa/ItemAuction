package baekgwa.itemauction.web.main;

import static org.mockito.ArgumentMatchers.anyLong;

import baekgwa.itemauction.IntegrationControllerTest;
import baekgwa.itemauction.annotation.WithCustomUser;
import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class MainControllerTest extends IntegrationControllerTest {

    @DisplayName("[Success] 인증된 회원이 없으면 Main Page 로 이동됩니다.")
    @Test
    void mainPageFormWithOutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @WithCustomUser
    @DisplayName("[Success] 인증된 회원이 접근하면 로그인된 메인페이지로 이동됩니다.")
    @Test
    void mainPageFormWithUser() throws Exception {
        UserProfileDataDto data = UserProfileDataDto.builder().name("테스트").nickName("백과").build();

        BDDMockito.given(userService.findUserData(null))
                        .willReturn(data);
                
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("loginMain"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("mainForm"));
    }
}