package baekgwa.itemauction.domain.userprofile.service;

import baekgwa.itemauction.web.main.MainForm;
import baekgwa.itemauction.web.mypage.MyPageForm;
import baekgwa.itemauction.web.mypage.MyPageForm.ChangeProfile;

public interface UserProfileService {
    void updateUserProfile(Long userId, ChangeProfile changeProfile);

    MainForm.UserInfo findUserDataMainForm(Long userId);

    MyPageForm.ProfileInfo findUserDataMyPageForm(Long userId);
}
