package baekgwa.itemauction.domain.user.service;

import baekgwa.itemauction.web.user.UserForm.NewUser;
import baekgwa.itemauction.web.user.UserResponse.CheckDuplicateLoginId;

public interface UserService {

    void addNewUser(NewUser newUser);

    CheckDuplicateLoginId checkDuplicateLoginId(String loginId);
}
