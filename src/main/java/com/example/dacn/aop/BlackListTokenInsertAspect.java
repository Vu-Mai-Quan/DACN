package com.example.dacn.aop;

import com.example.dacn.basetemplate.dto.response.LoginResponse;
import com.example.dacn.db2.model.BlackListToken;
import com.example.dacn.db2.repositories.BlackListTokenRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Aspect
@RequiredArgsConstructor
@Component
public class BlackListTokenInsertAspect {
    private final BlackListTokenRepo blackListTokenRepo;

    @AfterReturning(pointcut = "@annotation(com.example.dacn.config.annotations.BlackListToken)", returning = "object")
    @Transactional(transactionManager = "db2TransactionManager")
    protected void insertBlackListToken(JoinPoint joinPoint, Object object) {
        if (object instanceof LoginResponse loginResponse) {
            List<BlackListToken> blackListTokens = new ArrayList<>();
            BlackListToken blackTk = new BlackListToken(loginResponse.getIdLog(), loginResponse.getToken(), false),
                    blRfTk = loginResponse.isTaiCheToken() ? null : new BlackListToken(loginResponse.getIdLog(), loginResponse.getRefreshToken(), false);
            blackListTokens.add(blackTk);

            if (blRfTk != null) {
                blackListTokens.add(blRfTk);
            }

            blackListTokenRepo.saveAll(blackListTokens);
        }
    }
}
