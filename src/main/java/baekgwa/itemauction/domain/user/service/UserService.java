package baekgwa.itemauction.domain.user.service;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.web.user.UserForm.NewUser;
import baekgwa.itemauction.web.user.UserResponse;

public interface UserService {

    void addNewUser(NewUser newUser);

    UserProfileDataDto findUserData(Long id);

    UserResponse.checkDuplicateLoginId checkDuplicateLoginId(String loginId);
}
