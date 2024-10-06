package baekgwa.itemauction.web.user;

import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.web.user.UserForm.NewUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addNewUserForm(@ModelAttribute("newMemberForm") NewUser newUser) {

        return "/user/addUserForm";
    }

    @PostMapping("/add")
    public String addNewUserProc(@ModelAttribute("newMemberForm") NewUser newUser) {
        userService.addNewUser(newUser);
        return "redirect:/login";
    }
}

