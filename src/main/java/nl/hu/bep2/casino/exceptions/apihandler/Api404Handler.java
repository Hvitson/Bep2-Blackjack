package nl.hu.bep2.casino.exceptions.apihandler;

import nl.hu.bep2.casino.exceptions.ApiException;
import nl.hu.bep2.casino.exceptions.apirequest.Api404Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class Api404Handler {
    @ExceptionHandler(value = {Api404Exception.class})
    public ResponseEntity<Object> handleRequestException(Api404Exception e) {
        HttpStatus unauthorized = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                unauthorized,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, unauthorized);
    }
}
