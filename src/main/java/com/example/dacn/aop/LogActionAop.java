package com.example.dacn.aop;

import com.example.dacn.basetemplate.LogIdResponse;
import com.example.dacn.config.annotations.LogAction;
import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db2.model.LogCrud;
import com.example.dacn.db2.repositories.LogCrudRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class LogActionAop {

    private final LogCrudRepository logCrudRepository;

    @AfterReturning(value = "@annotation(logAction)", returning = "object")
    @Transactional(transactionManager = "db2TransactionManager")
    protected void logActionUser(JoinPoint joinPoint, LogAction logAction, Object object) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan = (TaiKhoan) authentication.getPrincipal();
        Set<LogCrud> logCruds = new HashSet<>();
        if (object instanceof Set<?> inObjects) {
            if (!inObjects.isEmpty() && inObjects.iterator().next() instanceof LogIdResponse) {
                inObjects.forEach(item ->
                        logCruds.add(getLogCrud(taiKhoan.getId(), logAction, (LogIdResponse) item))
                );
            }
        } else if (object instanceof LogIdResponse item) {
            logCruds.add(getLogCrud(taiKhoan.getId(), logAction, item));
        }
        if (!logCruds.isEmpty()) {
            logCrudRepository.saveAll(logCruds);
        }
    }

    private LogCrud getLogCrud(UUID id, LogAction logAction, LogIdResponse item) {
        LogCrud logCrud = new LogCrud();
        logCrud.setHanhVi(logAction.action());
        logCrud.setNoiDung(logAction.descriptions());
        logCrud.setIdNguoiDung(id);
        logCrud.setIdTuongTac(item.getIdLog());
        logCrud.setBangTuongTac(logAction.bangTuongTac());
        return logCrud;
    }

}
