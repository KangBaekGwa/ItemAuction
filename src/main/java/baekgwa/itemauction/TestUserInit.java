package baekgwa.itemauction;

import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestUserInit {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 실행시 사용할 Admin 용 계정 생성
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        User newUser = User.createNewAdmin("test1", bCryptPasswordEncoder.encode("1234"));
        User savedData = userRepository.save(newUser);

        UserProfile newUserProfile = UserProfile.createNewUserProfile(savedData, "유저A", "백과",
                "test@test.com", "01000000000");
        userProfileRepository.save(newUserProfile);
    }
}