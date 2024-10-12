package baekgwa.itemauction.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
@NotBlank(message = "비밀번호는 필수값 입니다.")
@Size(min = 8, max = 20, message = "비밀번호는 8자리 ~ 20자리 사이 입니다.")
@Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
        message = "비밀번호는 특수문자를 반드시 포함하여야 합니다.")
public @interface UserPassword {
    String message() default "유효하지 않은 패스워드 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
