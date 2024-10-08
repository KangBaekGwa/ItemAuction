package baekgwa.itemauction.web.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}