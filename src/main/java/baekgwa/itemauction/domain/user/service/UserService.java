package baekgwa.itemauction.domain.user.service;

import baekgwa.itemauction.web.user.UserForm.NewUser;
import baekgwa.itemauction.web.user.UserResponse;

public interface UserService {

    void addNewUser(NewUser newUser);

    UserResponse.checkDuplicateLoginId checkDuplicateLoginId(String loginId);
}
