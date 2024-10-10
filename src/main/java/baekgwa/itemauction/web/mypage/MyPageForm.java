package baekgwa.itemauction.web.mypage;

import lombok.Getter;
import lombok.Setter;

public class MyPageForm {

    @Getter
    @Setter
    public static class ChangeProfile {
        private String nickName;
        private String name;
        private String email;
        private String phone;
    }
}
