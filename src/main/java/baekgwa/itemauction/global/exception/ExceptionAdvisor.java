package baekgwa.itemauction.global.exception;

import baekgwa.itemauction.web.user.UserForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(CustomException.class)
    public String customExceptionHandler(CustomException e, Model model) {

        return switch (e.getCode()) {
            case CustomErrorCode.ADD_USER_ERROR_DUPLICATED_LOGIN_ID,
                 CustomErrorCode.ADD_USER_ERROR_DUPLICATED_EMAIL,
                 CustomErrorCode.ADD_USER_ERROR_DUPLICATED_PHONE,
                 CustomErrorCode.ADD_USER_ERROR_DUPLICATED_NICKNAME -> {
                addGlobalErrorAttribute(model, e.getCode().getMessage());
                model.addAttribute("newMemberForm", new UserForm.NewUser());
                yield "/user/addUserForm";
            }
            default -> {
                addGlobalErrorAttribute(model, CustomErrorCode.NOT_HANDLED_EXCEPTION.getMessage());
                yield "redirect:/";
            }
        };
    }

    private void addGlobalErrorAttribute(Model model, String message) {
        model.addAttribute("globalError", message);
    }
}
