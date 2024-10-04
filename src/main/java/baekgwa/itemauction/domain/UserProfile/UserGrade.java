package baekgwa.itemauction.domain.UserProfile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserGrade {

    BRONZE("정상 회원"),
    SILVER("차단 회원"),
    GOLD("탈퇴 회원"),
    PLATINUM("탈퇴 회원"),
    DIAMOND("탈퇴 회원"),
    VIP("탈퇴 회원");

    private final String text;
}
