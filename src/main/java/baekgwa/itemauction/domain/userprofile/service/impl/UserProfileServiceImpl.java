package baekgwa.itemauction.domain.userprofile.service.impl;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import baekgwa.itemauction.domain.userprofile.service.UserProfileService;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import baekgwa.itemauction.web.mypage.MyPageForm.ChangeProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    @Override
    public void updateUserProfile(Long userId, ChangeProfile changeProfile) {
        UserProfile findUserProfileData = userProfileRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND)
        );

        findUserProfileData.updateUserProfile(
                changeProfile.getNickName(), changeProfile.getName(),
                changeProfile.getEmail(), changeProfile.getPhone());
    }

    @Transactional
    @Override
    public UserProfileDataDto findUserDataMainForm(Long userId) {
        UserProfile findUserProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND)
        );
        return convertToDto(findUserProfile);
    }

    private UserProfileDataDto convertToDto(UserProfile userProfile) {
        return UserProfileDataDto
                .builder()
                .nickName(userProfile.getNickName())
                .name(userProfile.getName())
                .build();
    }
}
