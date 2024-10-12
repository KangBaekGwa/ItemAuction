package baekgwa.itemauction.web.main;

import baekgwa.itemauction.domain.userdetail.dto.CustomUserDetails;
import baekgwa.itemauction.domain.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserProfileService userProfileService;

    @GetMapping("/")
    public String mainPageForm(
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null) {
            MainForm.UserInfo data = userProfileService.findUserDataMainForm(userDetails.getId());
            model.addAttribute("mainForm", data);
            return "loginMain";
        }

        return "main";
    }
}
