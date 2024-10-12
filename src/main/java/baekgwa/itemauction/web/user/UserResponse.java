package baekgwa.itemauction.web.user;

import lombok.Builder;
import lombok.Getter;

public class UserResponse {

    @Getter
    public static class CheckDuplicateLoginId {
        boolean duplicate;

        @Builder
        private CheckDuplicateLoginId(boolean duplicate) {
            this.duplicate = duplicate;
        }
    }
}
