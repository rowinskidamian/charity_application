package pl.damianrowinski.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

//@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ExceptionsHandlerController {

    private static final String ERROR_PAGE_ATTRIBUTE = "errorMsg";
    private static final String ERROR_PAGE_VIEW = "/error";

    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleNotFoundException(Exception e, Model model) {
        log.error("Raised ObjectNotFoundException");
        model.addAttribute(ERROR_PAGE_ATTRIBUTE, e.getMessage());
        return ERROR_PAGE_VIEW;
    }

}
