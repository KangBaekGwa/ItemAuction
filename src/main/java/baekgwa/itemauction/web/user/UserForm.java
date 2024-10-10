package baekgwa.itemauction.web.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserForm {

    @Getter
    @Setter
    public static class NewUser {

        @NotBlank(message = "로그인 아이디는 필수값 입니다.")
        @Size(min = 5, max = 20, message = "로그인 아이디는 5자리 ~ 20자리 사이입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "로그인 아이디는 영문(대소문자 구분)과 숫자만 허용합니다.")
        private String loginId;

        @NotBlank(message = "비밀번호는 필수값 입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자리 ~ 20자리 사이 입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
                message = "비밀번호는 특수문자를 반드시 포함하여야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수값 입니다.")
        @Size(min = 2, max = 15, message = "이름은 2글자 ~ 15글자 사이입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글과 영문만 허용됩니다.")
        private String name;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2글자 ~ 10글자 사이입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 영문, 국문, 숫자만 허용합니다.")
        private String nickName;

        @NotBlank(message = "이메일은 필수값 입니다.")
        @Email(message = "잘못된 이메일 형식입니다.")
        private String email;

        @NotBlank(message = "전화번호는 필수값 입니다.")
        @Size(min = 8, max = 11, message = "전화번호는 8자리에서 11자리까지 입력해야 합니다.")
        @Pattern(regexp = "^[0-9]+$", message = "전화번호는 숫자만 허용됩니다.")
        private String phone;

        @Override
        public String toString() {
            return "NewUser{" +
                    "loginId='" + loginId + '\'' +
                    ", password='" + password + '\'' +
                    ", name='" + name + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}
