package baekgwa.itemauction.domain.userprofile.service.impl;

import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto;
import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto.ProfileInfo;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import baekgwa.itemauction.domain.userprofile.service.UserProfileService;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import baekgwa.itemauction.web.main.MainForm;
import baekgwa.itemauction.web.mypage.MyPageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    @Override
    public void updateUserProfile(Long userId, MyPageForm.ChangeProfile changeProfile) {

        updateUserProfileDuplicateCheck(changeProfile);

        UserProfile findUserProfileData = userProfileRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND)
        );

        findUserProfileData.updateUserProfile(
                changeProfile.getNickName(), changeProfile.getName(),
                changeProfile.getEmail(), changeProfile.getPhone());
    }

    @Transactional
    @Override
    public MainForm.UserInfo findUserDataMainForm(Long userId) {
        UserProfileDto.MainInfo mainInfo = userProfileRepository.findUserInfoMainFormByUserId(
                userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND)
        );
        return UserProfileDto.convertToMainForm(mainInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public MyPageForm.ProfileInfo findUserDataMyPageForm(Long userId) {
        ProfileInfo profileInfo = userProfileRepository.findUserInfoMyPageFormByUserId(
                userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND));

        return UserProfileDto.convertToMyPageForm(profileInfo);
    }

    private void updateUserProfileDuplicateCheck(MyPageForm.ChangeProfile changeProfile) {
        userProfileRepository.findFirstByEmailOrNickNameOrPhone(changeProfile.getNickName(),
                changeProfile.getEmail(), changeProfile.getPhone()).ifPresent(
                findData -> {
                    if (findData.getEmail().equals(changeProfile.getEmail())) {
                        throw new CustomException(CustomErrorCode.UPDATE_USER_PROFILE_ERROR_DUPLICATED_EMAIL);
                    }

                    if (findData.getPhone().equals(changeProfile.getPhone())) {
                        throw new CustomException(CustomErrorCode.UPDATE_USER_PROFILE_ERROR_DUPLICATED_PHONE);
                    }

                    if (findData.getNickName().equals(changeProfile.getNickName())) {
                        throw new CustomException(
                                CustomErrorCode.UPDATE_USER_PROFILE_ERROR_DUPLICATED_NICKNAME);
                    }
                }
        );
    }
}