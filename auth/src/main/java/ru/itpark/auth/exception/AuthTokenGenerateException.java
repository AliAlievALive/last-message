package ru.itpark.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthTokenGenerateException extends BaseAppException {

    public AuthTokenGenerateException() {
        super();
    }

    public AuthTokenGenerateException(String message) {
        super(message);
    }

    public AuthTokenGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthTokenGenerateException(Throwable cause) {
        super(cause);
    }

    protected AuthTokenGenerateException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
