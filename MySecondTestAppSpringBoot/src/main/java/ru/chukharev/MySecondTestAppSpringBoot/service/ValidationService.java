package ru.chukharev.MySecondTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.chukharev.MySecondTestAppSpringBoot.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
