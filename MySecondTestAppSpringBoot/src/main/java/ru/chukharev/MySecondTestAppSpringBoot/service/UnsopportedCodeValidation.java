package ru.chukharev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.chukharev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chukharev.MySecondTestAppSpringBoot.model.Request;

@Service
public class UnsopportedCodeValidation implements UnsopportedCodeService{
    @Override
    public void isValid(Request request, BindingResult bindingResult) throws UnsupportedCodeException {
        if (request.getUid().equals("123")){
            throw new UnsupportedCodeException(bindingResult.getFieldError().toString());
        }
    }
}
