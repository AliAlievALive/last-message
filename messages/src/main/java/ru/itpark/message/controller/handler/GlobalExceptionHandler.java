package ru.itpark.message.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itpark.message.exception.BaseAppException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseAppException.class)
    public final ResponseEntity<ExceptionMessage> handleAllExceptions(BaseAppException exception,
                                                                      HandlerMethod handlerMethod) {
        String message = exception.getMessage();
        log.error(message, exception);
        return new ResponseEntity<>(ExceptionMessage.builder()
                .message(message)
                .exceptionName(exception.getClass().getName())
                .build(), exception.getStatus());
    }

}

