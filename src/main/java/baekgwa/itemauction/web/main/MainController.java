package baekgwa.itemauction.web.main;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.domain.userdetail.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String mainPageForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            UserProfileDataDto userProfileDataDto = userService.findUserData(userDetails.getId());

            MainForm mainForm = MainForm.builder()
                    .userProfileDataDto(userProfileDataDto)
                    .build();

            model.addAttribute(mainForm);

            return "loginMain";
        }

        return "main";
    }
}
