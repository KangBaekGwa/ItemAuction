package baekgwa.itemauction.web.main;

import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainForm {

    private final UserProfileDataDto userProfileDataDto;

    @Builder
    private MainForm(UserProfileDataDto userProfileDataDto) {
        this.userProfileDataDto = userProfileDataDto;
    }
}
