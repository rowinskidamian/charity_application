package pl.damianrowinski.charity.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.charity.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

@ControllerAdvice(basePackages = "pl.damianrowinski.charity.rest")
@Slf4j
public class ExceptionHandlerRestController {
    @ResponseBody
    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String objectNotFoundStatus(ObjectNotFoundException ex) {
        log.error("Raised ObjectNotFoundApiException");
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({ObjectInRelationshipException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String objectInRelationship(ObjectInRelationshipException ex) {
        log.error("Raised ObjectInRelationshipException");
        return ex.getMessage();
    }

}
