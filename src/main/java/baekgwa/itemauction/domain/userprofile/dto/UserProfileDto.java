package baekgwa.itemauction.domain.userprofile.dto;

import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.web.main.MainForm;
import baekgwa.itemauction.web.mypage.MyPageForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserProfileDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileInfo {

        private String nickName;
        private String name;
        private String email;
        private String phone;
        private UserGrade userGrade;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MainInfo {
        private String nickName;
        private String name;
    }

    public static MyPageForm.ProfileInfo convertToMyPageForm(ProfileInfo profileInfo) {
        return MyPageForm.ProfileInfo
                .builder()
                .nickName(profileInfo.getNickName())
                .name(profileInfo.getName())
                .email(profileInfo.getEmail())
                .phone(profileInfo.getPhone())
                .userGrade(profileInfo.getUserGrade().getText())
                .build();
    }

    public static MainForm.UserInfo convertToMainForm(MainInfo mainInfo) {
        return MainForm.UserInfo
                .builder()
                .nickName(mainInfo.getNickName())
                .name(mainInfo.getName())
                .build();
    }
}