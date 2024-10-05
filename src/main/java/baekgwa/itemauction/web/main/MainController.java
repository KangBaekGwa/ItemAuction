package baekgwa.itemauction.web.main;

import baekgwa.itemauction.domain.user.dto.UserPrifileDataDto;
import baekgwa.itemauction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

//    @GetMapping("/")
//    public String mainPage(@ModelAttribute("mainForm") MainForm mainForm) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication.getPrincipal() instanceof String) {
//            return "main";
//        }
//
//        UserPrifileDataDto findData = userService.findUserData(authentication.getName());
//        mainForm.setUserPrifileDataDto(findData);
//
//        return "loginMain";
//    }

    @GetMapping("/")
    public String mainPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            return "main";
        }

        UserPrifileDataDto findUserProfileDataDto = userService.findUserData(
                authentication.getName());

        MainForm mainForm = MainForm.builder()
                .userPrifileDataDto(findUserProfileDataDto)
                .build();

        model.addAttribute(mainForm);

        return "loginMain";
    }
}
