package ru.chukharev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.chukharev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.chukharev.MySecondTestAppSpringBoot.model.Request;

@Service
public interface UnsopportedCodeService {
    public void isValid(Request request, BindingResult bindingResult) throws UnsupportedCodeException;
}
