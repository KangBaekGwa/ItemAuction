package baekgwa.itemauction.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPrifileDataDto {

    private String name;
    private String nickName;

    @Builder
    private UserPrifileDataDto(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }
}
