package ru.chukharev.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.chukharev.MySecondTestAppSpringBoot.model.Response;
import ru.chukharev.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.security.PublicKey;
import java.util.Date;
@Slf4j
@Service
@Qualifier
public class ModifySystemTimeResponseService implements  ModifyResponseService{
    @Override
    public Response modify(Response response){
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("response: {}", response);
        return response;
    }
}
