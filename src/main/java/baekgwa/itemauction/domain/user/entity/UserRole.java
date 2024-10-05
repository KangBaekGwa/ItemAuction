package baekgwa.itemauction.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    NONE("권한 없음"),
    BUYER("구매 권한"),
    TRADER("판매/구매 권한"),
    ADMIN("관리자 권한");

    private final String text;
}
