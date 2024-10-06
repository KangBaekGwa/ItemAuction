package baekgwa.itemauction;

import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.global.configuration.SecurityConfig;
import baekgwa.itemauction.web.login.LoginController;
import baekgwa.itemauction.web.logout.LogoutController;
import baekgwa.itemauction.web.main.MainController;
import baekgwa.itemauction.web.user.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        LoginController.class,
//        LogoutController.class,
        MainController.class,
        UserController.class,
})
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public abstract class IntegrationControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;
}
