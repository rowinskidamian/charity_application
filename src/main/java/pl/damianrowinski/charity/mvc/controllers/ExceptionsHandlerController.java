package pl.damianrowinski.charity.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

@ControllerAdvice(basePackages = "pl.damianrowinski.charity.mvc.controllers")
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

}
