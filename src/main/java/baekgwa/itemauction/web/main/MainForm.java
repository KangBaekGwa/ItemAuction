package baekgwa.itemauction.web.main;

import lombok.Builder;
import lombok.Getter;

public class MainForm {

    @Getter
    public static class UserInfo {
        private final String name;
        private final String nickName;

        @Builder
        private UserInfo(String name, String nickName) {
            this.name = name;
            this.nickName = nickName;
        }
    }
}
