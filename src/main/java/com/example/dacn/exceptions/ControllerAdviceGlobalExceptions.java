package com.example.dacn.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdviceGlobalExceptions {

// Không có tác dụng
//    @ExceptionHandler(JwtException.class)
//    ResponseEntity<ProblemDetail> jwtExceptions(@NonNull JwtException e, @NonNull HttpServletRequest request) {
//        BuilderProblem pro = BuilderProblem.builder()
//                .status(401)
//                .title("401: Unauthorized")
//                .detail("Jwt token is invalid")
//                .instance(request.getRequestURI())
//                .build();
//
//        return createProblemDetailResponse(pro, r -> {
//            switch (e) {
//                case InvalidKeyException ignored -> r.setDetail("Khóa token bị lỗi");
//                case ExpiredJwtException ignored -> r.setDetail("Token đã hết hạn xử dụng");
//                case SignatureException ignored -> r.setDetail("Khóa bảo mật không hợp lệ");
//                default -> r.setDetail(e.getMessage());
//            }
//        });
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> invalidFieldValidation(MethodArgumentNotValidException bindingResult,
                                                         HttpServletRequest request) {
        var build = BuilderProblem.builder()
                .status(400)
                .title("404: Validator exceptions")
                .detail("Request validation failed")
                .instance(request.getRequestURI())
                .build();
        Map<String, List<String>> mapError = bindingResult.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())
                ));
        return createProblemDetailResponse(build, r -> r.setProperty("error", mapError));
    }

    @Builder
    private record BuilderProblem(String title, String detail, String instance, int status) {
    }

    @NonNull
    private ResponseEntity<ProblemDetail> createProblemDetailResponse(@NonNull BuilderProblem builderProblem,
                                                                      @NonNull Consumer<ProblemDetail> predicate) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(builderProblem.status());
        problemDetail.setTitle(builderProblem.title());
        problemDetail.setDetail(builderProblem.detail());
        problemDetail.setInstance(URI.create(builderProblem.instance()));
        predicate.accept(problemDetail);
        return ResponseEntity.status(builderProblem.status()).body(problemDetail);
    }
}
