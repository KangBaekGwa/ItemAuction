package baekgwa.itemauction.web.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class UserForm {

    @Getter
    @Setter
    public static class NewUser {

        private String loginId;
        private String password;
        private String name;
        private String nickName;
        private String email;
        private String phone;
    }
}
