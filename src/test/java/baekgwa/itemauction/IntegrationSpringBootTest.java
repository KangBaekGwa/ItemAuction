package baekgwa.itemauction;

import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.user.service.UserService;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import baekgwa.itemauction.domain.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationSpringBootTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserProfileRepository userProfileRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserProfileService userProfileService;
}
