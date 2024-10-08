package baekgwa.itemauction.domain.user.service;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.web.user.UserForm.NewUser;

public interface UserService {

    void addNewUser(NewUser newUser);

    UserProfileDataDto findUserData(Long id);
}
