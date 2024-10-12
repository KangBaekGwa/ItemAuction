package baekgwa.itemauction.web.user;

import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.web.user.UserResponse.CheckDuplicateLoginId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addNewUserForm(@ModelAttribute("newMemberForm") UserForm.NewUser newUser) {
        return "user/addUserForm";
    }

    @PostMapping("/add")
    public String addNewUserProc(
            @ModelAttribute("newMemberForm") @Valid UserForm.NewUser newUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/addUserForm";
        }

        userService.addNewUser(newUser);
        return "redirect:/login";
    }

    @GetMapping("/add/check")
    public ResponseEntity<CheckDuplicateLoginId> checkDuplicateLoginId(
            @RequestParam(name = "loginId") String loginId
    ) {
        CheckDuplicateLoginId result = userService.checkDuplicateLoginId(loginId);
        return ResponseEntity.ok(result);
    }
}

