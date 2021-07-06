package ru.itpark.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthTokenParseException extends BaseAppException {

    public AuthTokenParseException() {
        super();
    }

    public AuthTokenParseException(String message) {
        super(message);
    }

    public AuthTokenParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthTokenParseException(Throwable cause) {
        super(cause);
    }

    protected AuthTokenParseException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
