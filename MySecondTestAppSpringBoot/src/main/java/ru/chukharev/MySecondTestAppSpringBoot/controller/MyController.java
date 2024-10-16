package ru.chukharev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chukharev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chukharev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.chukharev.MySecondTestAppSpringBoot.model.*;
import ru.chukharev.MySecondTestAppSpringBoot.service.UnsopportedCodeService;
import ru.chukharev.MySecondTestAppSpringBoot.service.ValidationService;
import ru.chukharev.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final UnsopportedCodeService unsopportedCodeService;
    @Autowired
    public MyController(ValidationService validationService, UnsopportedCodeService unsopportedCodeService){
        this.validationService=validationService;
        this.unsopportedCodeService=unsopportedCodeService;
    }
    @PostMapping(value="/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult){
        log.info("request: {}", request);


        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCES)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("response: {}", response);

        try {
            validationService.isValid(bindingResult);
            unsopportedCodeService.isValid(request,bindingResult);
        } catch (UnsupportedCodeException e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSOPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSOPPORTED);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (ValidationFailedException e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
