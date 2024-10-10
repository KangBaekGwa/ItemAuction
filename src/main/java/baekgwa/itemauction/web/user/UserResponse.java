package baekgwa.itemauction.web.user;

import lombok.Builder;
import lombok.Getter;

public class UserResponse {

    @Getter
    public static class checkDuplicateLoginId {
        boolean duplicate;

        @Builder
        private checkDuplicateLoginId(boolean duplicate) {
            this.duplicate = duplicate;
        }
    }
}
