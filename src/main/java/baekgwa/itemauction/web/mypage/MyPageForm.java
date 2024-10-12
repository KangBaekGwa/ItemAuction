package baekgwa.itemauction.web.mypage;

import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.global.validation.annotation.UserEmail;
import baekgwa.itemauction.global.validation.annotation.UserName;
import baekgwa.itemauction.global.validation.annotation.UserNickName;
import baekgwa.itemauction.global.validation.annotation.UserPhone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MyPageForm {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProfileInfo {

        @UserNickName
        private String nickName;
        @UserName
        private String name;
        @UserEmail
        private String email;
        @UserPhone
        private String phone;

        private String userGrade;

        @Builder
        private ProfileInfo(String nickName, String name, String email, String phone,
                String userGrade) {
            this.nickName = nickName;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.userGrade = userGrade;
        }
    }

    @Getter
    public static class ChangeProfile {

        private final String nickName;
        private final String name;
        private final String email;
        private final String phone;

        @Builder
        private ChangeProfile(String name, String nickName, String email, String phone) {
            this.name = name;
            this.nickName = nickName;
            this.email = email;
            this.phone = phone;
        }
    }

    public static ChangeProfile convertToChangeProfile(ProfileInfo profileInfo) {
        return ChangeProfile
                .builder()
                .nickName(profileInfo.getNickName())
                .name(profileInfo.getName())
                .email(profileInfo.getEmail())
                .phone(profileInfo.getPhone())
                .build();
    }
}
