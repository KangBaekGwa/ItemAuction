package baekgwa.itemauction.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileDataDto {

    private String name;
    private String nickName;

    @Builder
    private UserProfileDataDto(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }
}
