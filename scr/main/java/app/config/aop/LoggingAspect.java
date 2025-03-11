  package app.config.aop;

  import lombok.Getter;
  import lombok.extern.slf4j.Slf4j;
  import org.aspectj.lang.JoinPoint;
  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.aspectj.lang.annotation.Before;
  import org.springframework.stereotype.Component;

  import java.time.Duration;
  import java.time.Instant;

  @Getter
  @Slf4j
  @Component
  @Aspect
  public class LoggingAspect {
    private int counter;

    public void resetCounter() {
      counter = 0;
    }

    @Before("execution(* app.api.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
      counter++;
      log.info("Перед вызовом метода: " +
          joinPoint.getSignature().getName());
    }

    @Around("execution(* app.api.controller.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint)
        throws Throwable {
      counter++;
      Instant startTime = Instant.now();
      Object result = joinPoint.proceed();
      Instant endTime = Instant.now();
      Duration duration = Duration.between(startTime, endTime);
      log.info("Метод " + joinPoint.getSignature().getName()
          + " выполнен за " + duration.toMillis() + " мс");
      return result;
    }
  }