package gestion_conges.server.controllers;

import gestion_conges.server.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice
{
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDTO handleException(RuntimeException exception)
    {
        exception.printStackTrace();
        return new ErrorDTO(exception);
    }
}
