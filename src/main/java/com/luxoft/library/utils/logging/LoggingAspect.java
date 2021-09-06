package com.luxoft.library.utils.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

import static com.luxoft.library.utils.MainUtil.parser;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.luxoft.library.controllers.BookController.getById(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {

        String[] args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .toArray(String[]::new);

        // input arg names, types
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Class<?>[] parameterTypes = codeSignature.getParameterTypes();

        int argCount = args.length;

        // method name and class name
        String shortName = jp.toShortString();
        String regex = "\\.[\\w]+\\(";
        String methodName = parser(regex, shortName);

        String classNameDirectory = shortName.split("execution\\(")[1].split("\\.")[0];

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("-".repeat(150));
        builder.append("\nCOMMAND CALL:");
        builder.append("\n\t* from Class name - ").append(classNameDirectory);
        builder.append("\n\t* with method name - ").append(methodName);
        if (argCount != 0) {
            builder.append("\n\t* with arguments:");
            for (int i = 0; i < argCount; i++) {
                builder.append("\n\t\t Name: ").append(parameterNames[i]).append(", ");
                builder.append(" Type: ").append(parameterTypes[i].toString()).append(", ");
                builder.append(" Input Value: ").append(args[i]);
            }
        } else {
            builder.append("\n\t* with no arguments ");
        }

        long start = System.currentTimeMillis();
        ResponseEntity<?> proceedResult = (ResponseEntity<?>) jp.proceed();
        long executionTime = System.currentTimeMillis() - start;

        // method specific information
        addResponseInformationFromResponseEntity(builder, proceedResult);
        builder.append("\n\t* execution time - ").append(executionTime).append(" ms");
        builder.append("\n");
        builder.append("-".repeat(150));

        logger.info(builder.toString());
        return proceedResult;
    }

    private void addResponseInformationFromResponseEntity(StringBuilder builder, ResponseEntity<?> response) {
        builder.append("\n\t* execution status - ").append(response.getStatusCode().value());
        builder.append("\n\t* execution return response body - ").append(Objects.requireNonNull(response.getBody()));
    }
}
