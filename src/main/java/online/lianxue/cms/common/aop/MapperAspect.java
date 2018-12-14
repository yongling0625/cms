package online.lianxue.cms.common.aop;

import online.lianxue.cms.shiro.JWTUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Mapper切面，插入创建人，创建时间，修改人，修改时间
 */
@Aspect
@Component
@Configuration
public class MapperAspect {
	private static final String createBy = "createBy";
	private static final String createTime = "createTime";
	private static final String lastUpdateBy = "lastUpdateBy";
	private static final String lastUpdateTime = "lastUpdateTime";

	@Pointcut("execution(* online.lianxue.cms.*.mapper.*.update*(..))")
	public void daoUpdate() {
	}

	@Pointcut("execution(* online.lianxue.cms.*.mapper.*.insert*(..))")
	public void daoCreate() {
	}

	@Around("daoUpdate()")
	public Object doAroundUpdate(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return pjp.proceed();
		}
		HttpServletRequest request = attributes.getRequest();
		String token = request.getHeader("token");
		String username = JWTUtil.getUsername(token);
		if (token != null && username != null) {
			Object[] objects = pjp.getArgs();
			if (objects != null && objects.length > 0) {
				for (Object arg : objects) {
					BeanUtils.setProperty(arg, lastUpdateBy, username);
					BeanUtils.setProperty(arg, lastUpdateTime, LocalDateTime.now());
				}
			}
		}
		return pjp.proceed();
	}

	@Around("daoCreate()")
	public Object doAroundCreate(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return pjp.proceed();
		}
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
		Object[] objects = pjp.getArgs();
		if (objects != null && objects.length > 0) {
			for (Object arg : objects) {
				String username = JWTUtil.getUsername(token);
				if (username != null) {
					if (StringUtils.isBlank(BeanUtils.getProperty(arg, createBy))) {
						BeanUtils.setProperty(arg, createBy, username);
					}
					if (StringUtils.isBlank(BeanUtils.getProperty(arg, createTime))) {
						BeanUtils.setProperty(arg, createTime, LocalDateTime.now());
					}
				}
			}
		}
        return pjp.proceed();
	}
}