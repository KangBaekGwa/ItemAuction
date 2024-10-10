package baekgwa.itemauction.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final CustomErrorCode code;

    public CustomException(CustomErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
