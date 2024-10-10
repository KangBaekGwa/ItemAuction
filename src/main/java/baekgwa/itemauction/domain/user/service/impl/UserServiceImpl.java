package baekgwa.itemauction.domain.user.service.impl;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import baekgwa.itemauction.web.user.UserForm.NewUser;
import baekgwa.itemauction.web.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void addNewUser(NewUser newUser) {

        addNewUserDuplicateCheck(newUser);

        User createdNewUser = User.createNewUser(newUser.getLoginId(),
                encodePassword(newUser.getPassword()));
        User savedUserData = userRepository.save(createdNewUser);

        UserProfile createdNewUserProfile = UserProfile.createNewUserProfile(savedUserData,
                newUser.getName(), newUser.getNickName(), newUser.getEmail(), newUser.getPhone());
        userProfileRepository.save(createdNewUserProfile);
    }

    @Transactional
    @Override
    public UserProfileDataDto findUserData(Long userId) {
        UserProfile findUserProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND)
        );
        return convertToDto(findUserProfile);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse.checkDuplicateLoginId checkDuplicateLoginId(String loginId) {
        return UserResponse.checkDuplicateLoginId.builder()
                .duplicate(userRepository.existsByLoginId(loginId))
                .build();
    }

    private UserProfileDataDto convertToDto(UserProfile userProfile) {
        return UserProfileDataDto
                .builder()
                .nickName(userProfile.getNickName())
                .name(userProfile.getName())
                .build();
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private void addNewUserDuplicateCheck(NewUser newUser) {
        if (Boolean.TRUE.equals(userRepository.existsByLoginId(newUser.getLoginId()))) {
            throw new CustomException(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_LOGIN_ID);
        }

        userProfileRepository.findAllByUniqueValues(newUser.getNickName(), newUser.getEmail(),
                newUser.getPhone()).ifPresent(
                findData -> {
                    if (findData.getEmail().equals(newUser.getEmail())) {
                        throw new CustomException(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_EMAIL);
                    }

                    if (findData.getPhone().equals(newUser.getPhone())) {
                        throw new CustomException(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_PHONE);
                    }

                    if (findData.getNickName().equals(newUser.getNickName())) {
                        throw new CustomException(
                                CustomErrorCode.ADD_USER_ERROR_DUPLICATED_NICKNAME);
                    }
                }
        );
    }
}
