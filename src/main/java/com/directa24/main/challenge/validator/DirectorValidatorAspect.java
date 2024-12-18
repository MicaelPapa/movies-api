package com.directa24.main.challenge.validator;

import com.directa24.main.challenge.exception.InvalidThresholdException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DirectorValidatorAspect {
    private static final Logger logger = LoggerFactory.getLogger(DirectorValidatorAspect.class);

    @Before("execution(* com.directa24.main.challenge.controller.DirectorController.getDirectors(int)) && args(threshold)")
    public void validateThreshold(JoinPoint joinPoint, int threshold) {
        if (threshold < 0) {
            logger.error("Threshold must be a positive integer.");
            throw new InvalidThresholdException("Threshold must be a positive integer.");
        }
    }
}
