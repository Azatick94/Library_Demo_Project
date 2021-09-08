package com.luxoft.library.utils.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

import static com.luxoft.library.utils.MainUtil.*;

@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(aspectLogger)")
    public Object aroundAdvice(ProceedingJoinPoint jp, AspectLogger aspectLogger) throws Throwable {

        ResponseEntity<?> proceedResult = null;

        String[] args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .toArray(String[]::new);

        // input arg names, types
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Class<?>[] parameterTypes = codeSignature.getParameterTypes();

        int argCount = args.length;

        // method name and class name
        String methodName = parserShortClassName(jp.toShortString());

        String classShortName = jp.toShortString().split("execution\\(")[1].split("\\.")[0];

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("-".repeat(150));
        builder.append("\nCOMMAND CALL:");
        builder.append("\n\t* from Class name - ").append(classShortName);
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

        if (aspectLogger.containsResponseEntity()) {
            proceedResult = (ResponseEntity<?>) jp.proceed();
            addResponseInformationFromResponseEntity(builder, proceedResult);
        } else {
            jp.proceed();
        }

        long executionTime = System.currentTimeMillis() - start;
        builder.append("\n\t* execution time - ").append(executionTime).append(" ms");
        builder.append("\n");
        builder.append("-".repeat(150));

        // find class name
        String classFullName = parserLongClassName(jp.toLongString());
        Class<?> aClass = Class.forName(classFullName);
        Logger logger = LoggerFactory.getLogger(aClass);
        logger.info(builder.toString());

        return proceedResult;
    }

    private void addResponseInformationFromResponseEntity(StringBuilder builder, ResponseEntity<?> response) {
        builder.append("\n\t* execution status - ").append(response.getStatusCode().value());
        builder.append("\n\t* execution return response body - ").append(Objects.requireNonNull(response.getBody()));
    }
}
