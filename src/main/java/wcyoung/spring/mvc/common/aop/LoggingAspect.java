package wcyoung.spring.mvc.common.aop;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Around(value = "execution(* wcyoung.spring.mvc.web..*Service.*(..))"
            + " or execution(* wcyoung.spring.mvc.mapper..*Mapper.*(..))")
    public Object logMethodRunningTime(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object returnValue = pjp.proceed();
        stopWatch.stop();

        long runningTime = stopWatch.getTime();
        log.debug("{}.{}() running time=({} ms)", className, methodName, String.format("%,d", runningTime));

        return returnValue;
    }

}
