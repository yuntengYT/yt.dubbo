package yt.dubbo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.MDC;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Configuration
@Slf4j
public class SpringAOP {

	private static final Logger logger = LoggerFactory.getLogger(SpringAOP.class);

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	/**
	 * 定义切点Pointcut
	 * 第一个*号：表示返回类型， *号表示所有的类型
	 * 第二个*号：表示类名，*号表示所有的类
	 * 第三个*号：表示方法名，*号表示所有的方法
	 * 后面括弧里面表示方法的参数，两个句点表示任何参数
	 */
	@Pointcut("execution(*  yt.dubbo.controller.*.*(..))")
	public void executionService() {}

	/**
	 * 方法调用之前调用
	 * @param joinPoint
	 */
	@Before(value = "executionService()")
	public void doBefore(JoinPoint joinPoint) {
		String requestId = String.valueOf(UUID.randomUUID());
		MDC.put("requestId", requestId);
		logger.info("=====>requestId：{}", requestId);
		logger.info("=====>@Before：请求参数为：{}", Arrays.toString(joinPoint.getArgs()));

		ThreadContext.put("requestId", UUID.randomUUID().toString());

		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			startTime.set(System.currentTimeMillis());
			HttpServletRequest request = attributes.getRequest();
			log.info("==Request URL {}, Args {}", request.getRequestURL(), "as");
		}
	}

	/**
	 * 方法之后调用
	 * @param joinPoint
	 * @param returnValue 方法返回值
	 */
	@AfterReturning(pointcut = "executionService()",returning="returnValue")
	public void  doAfterReturning(JoinPoint joinPoint,Object returnValue){
		logger.info("=====>@AfterReturning：响应参数为：{}",returnValue);
		// 处理完请求，返回内容
		MDC.clear();
		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			log.info("==RESPONSE URL {}, Args {}, Cost {}ms ret {}", request.getRequestURL(), "12");
		}
	}
}
