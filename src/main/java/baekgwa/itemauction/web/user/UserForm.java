package baekgwa.itemauction.web.user;

import baekgwa.itemauction.global.validation.annotation.UserEmail;
import baekgwa.itemauction.global.validation.annotation.UserName;
import baekgwa.itemauction.global.validation.annotation.UserLoginId;
import baekgwa.itemauction.global.validation.annotation.UserNickName;
import baekgwa.itemauction.global.validation.annotation.UserPassword;
import baekgwa.itemauction.global.validation.annotation.UserPhone;
import lombok.Getter;
import lombok.Setter;

public class UserForm {

    @Getter
    @Setter
    public static class NewUser {

        @UserLoginId
        private String loginId;

        @UserPassword
        private String password;

        @UserName
        private String name;

        @UserNickName
        private String nickName;

        @UserEmail
        private String email;

        @UserPhone
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
