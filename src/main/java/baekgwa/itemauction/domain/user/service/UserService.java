package baekgwa.itemauction.domain.user.service;

import baekgwa.itemauction.domain.user.dto.UserPrifileDataDto;
import baekgwa.itemauction.web.user.UserForm.NewUser;

public interface UserService {

    public void addNewUser(NewUser newUser);

    public UserPrifileDataDto findUserData(String loginId);
}
