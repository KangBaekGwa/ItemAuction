package baekgwa.itemauction.domain.user.service.impl;

import baekgwa.itemauction.domain.user.dto.UserPrifileDataDto;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.web.user.UserForm.NewUser;
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
        if (Boolean.TRUE.equals(duplicateCheckByLoginId(newUser.getLoginId()))) {
//            throw new CustomException(BaseResponse.DuplicatedLoginId);
            log.info("로그인 아이디가 중복되었습니다.");
            return;
        }

        User createdNewUser =
                User.createNewUser(
                        newUser.getLoginId(), bCryptPasswordEncoder.encode(newUser.getPassword()));
        UserProfile createdNewUserProfile =
                UserProfile.createNewUserProfile(
                        createdNewUser, newUser.getName(), newUser.getNickName(), newUser.getEmail(), newUser.getPhone());

        userRepository.save(createdNewUser);
        userProfileRepository.save(createdNewUserProfile);
    }

    @Transactional
    @Override
    public UserPrifileDataDto findUserData(String loginId) {
        User findData = userRepository.findByLoginId(loginId);
        UserProfile findUserProfile = userProfileRepository.findById(findData.getId()).orElseThrow(
                () -> new RuntimeException("로그인한 회원의 프로파일이 없음.")
        );
        return convertToDto(findUserProfile);
    }

    private boolean duplicateCheckByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    private UserPrifileDataDto convertToDto(UserProfile userProfile) {
        return UserPrifileDataDto
                .builder()
                .nickName(userProfile.getNickName())
                .name(userProfile.getName())
                .build();
    }
}
