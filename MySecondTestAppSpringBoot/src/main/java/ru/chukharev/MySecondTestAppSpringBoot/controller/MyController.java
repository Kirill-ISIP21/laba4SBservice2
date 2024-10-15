package ru.chukharev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chukharev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chukharev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.chukharev.MySecondTestAppSpringBoot.model.Request;
import ru.chukharev.MySecondTestAppSpringBoot.model.Response;
import ru.chukharev.MySecondTestAppSpringBoot.service.UnsopportedCodeService;
import ru.chukharev.MySecondTestAppSpringBoot.service.ValidationService;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isValid(bindingResult);
            unsopportedCodeService.isValid(request,bindingResult);
        } catch (UnsupportedCodeException e){
            response.setCode("failed");
            response.setErrorCode("UnsopportedCodeException");
            response.setErrorMessage("Не поддерживаемая ошибка");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (ValidationFailedException e){
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
