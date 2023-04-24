package com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/*
 * FileName : LogAspectJ.java
 * :: XML 에 선언적으로 aspect 의 적용
 */
public class LogAspectJ {

    // Constructor
    public LogAspectJ() {
        System.out.println("\nCommon :: " + this.getClass() + "\n");
    }

    // Around Advice
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("");
        System.out.println("[Around before] Target object method: " +
                joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName());

        // Check if the method has any arguments
        if (joinPoint.getArgs().length != 0) {
            System.out.println("[Around before] Method argument: " + joinPoint.getArgs()[0]);
        }

        // Invoke the target object's method
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Exception e) {
            System.out.println("[Around Error] An error occurred while invoking the method: " + e.getMessage());
            throw e; // re-throw the exception to let the caller handle it
        }

        System.out.println("[Around after] Target object return value: " + obj);
        System.out.println("");

        return obj;
    }

} // end of class
