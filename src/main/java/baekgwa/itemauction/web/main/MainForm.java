package baekgwa.itemauction.web.main;

import baekgwa.itemauction.domain.user.dto.UserPrifileDataDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainForm {

    private final UserPrifileDataDto userPrifileDataDto;

    @Builder
    private MainForm(UserPrifileDataDto userPrifileDataDto) {
        this.userPrifileDataDto = userPrifileDataDto;
    }
}
