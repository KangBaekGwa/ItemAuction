package baekgwa.itemauction.web.mypage;

import baekgwa.itemauction.domain.userdetail.dto.CustomUserDetails;
import baekgwa.itemauction.domain.userprofile.service.UserProfileService;
import baekgwa.itemauction.web.mypage.MyPageForm.ProfileInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final UserProfileService userProfileService;

    @GetMapping
    public String myPage(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return "myPage/myPageHome";
    }

    @GetMapping("/profiles")
    public String myProfileForm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {
        ProfileInfo profileInfo = userProfileService.findUserDataMyPageForm(userDetails.getId());
        model.addAttribute("profileForm", profileInfo);
        return "myPage/profile";
    }

    @PostMapping("/profiles")
    public String editProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute("profileForm") MyPageForm.ProfileInfo profileInfo,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "myPage/profile";
        }

        userProfileService.updateUserProfile(
                userDetails.getId(),
                MyPageForm.convertToChangeProfile(profileInfo));

        return "redirect:/mypage";
    }
}
