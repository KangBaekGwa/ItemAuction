package baekgwa.itemauction.domain.userprofile.service;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.web.mypage.MyPageForm.ChangeProfile;

public interface UserProfileService {
    void updateUserProfile(Long userId, ChangeProfile changeProfile);

    UserProfileDataDto findUserDataMainForm(Long userId);
}
