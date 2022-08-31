package com.spring.baitap10.exception;

import com.spring.baitap10.common.Response;
import com.spring.baitap10.common.ResponseBody;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BaseErrorHandlers {

    private static final Logger log = LoggerFactory.getLogger(BaseErrorHandlers.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    @Hidden
    public ResponseBody handleException(EntityNotFoundException exception) {
        log.error("EntityNotFound Exception Error: ", exception);
        return new ResponseBody(Response.OBJECT_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoResultException.class)
    @Hidden
    public ResponseBody handleException(NoResultException exception) {
        log.error("NoResult Exception Error: ", exception);
        return new ResponseBody(Response.DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @Hidden
    public ResponseBody handleGeneralException(Exception ex) {
        log.error("General Error: ", ex);
        return new ResponseBody(Response.SYSTEM_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseBody handleCommonException(CommonException e) {
        log.error("Common exception Error: ", e);
        return new ResponseBody(e.getResponse(), e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseBody handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("ResourceNotFoundException Error: ", e);
        return new ResponseBody(e.getResourceName(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBody methodArgumentNotValid(Exception e){
        if (e instanceof ConstraintViolationException)
            return new ResponseBody(Response.PARAM_NOT_VALID, e.getMessage());
        return new ResponseBody(
                Response.MISSING_PARAM,
                ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().stream()
                        .map(error -> {
                            int lastIndexOfDot = error.getField().lastIndexOf('.');
                            String newField = error.getField()
                                    .substring(lastIndexOfDot + 1, error.getField().length());

                            try {
                                return new FieldErrorVM(
                                        error.getObjectName(),
                                        newField,
                                       error.getDefaultMessage()
                                );
                            } catch (Exception ex) {
                                return new FieldErrorVM(
                                        "0000",
                                        "not error code",
                                        "Default"
                                );
                            }
                        }).collect(Collectors.toList())
        );
    }


    public class FieldErrorVM implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String objectName;

        private final String field;

        private final String message;

        public FieldErrorVM(String dto, String field, String message) {
            this.objectName = dto;
            this.field = field;
            this.message = message;
        }

        public String getObjectName() {
            return objectName;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }


}
