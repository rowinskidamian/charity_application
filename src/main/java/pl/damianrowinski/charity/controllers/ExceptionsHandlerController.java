package pl.damianrowinski.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundApiException;

@ControllerAdvice
@Slf4j
public class ExceptionsHandlerController {

    private static final String ERROR_PAGE_ATTRIBUTE = "errorMsg";
    private static final String ERROR_PAGE_VIEW = "/error_page";

    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleNotFoundException(Exception e, Model model) {
        log.error("Raised ObjectNotFoundException");
        model.addAttribute(ERROR_PAGE_ATTRIBUTE, e.getMessage());
        return ERROR_PAGE_VIEW;
    }

    @ResponseBody
    @ExceptionHandler(ObjectNotFoundApiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String objectNotFoundStatus(ObjectNotFoundApiException ex) {
        log.error("Raised ObjectNotFoundRestException");
        return ex.getMessage();
    }

}
