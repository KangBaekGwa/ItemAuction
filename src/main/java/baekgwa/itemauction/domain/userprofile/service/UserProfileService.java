package baekgwa.itemauction.domain.userprofile.service;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.web.mypage.MyPageForm;

public interface UserProfileService {
    void updateUserProfile(Long userId, MyPageForm.changeProfile changeProfile);

    UserProfileDataDto findUserDataMainForm(Long userId);
}
