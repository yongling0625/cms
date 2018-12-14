package online.lianxue.cms.common.aop;

import lombok.extern.slf4j.Slf4j;
import online.lianxue.cms.common.util.IPUtil;
import online.lianxue.cms.system.entity.SysLog;
import online.lianxue.cms.system.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    //设置切入点：这里直接拦截被@RestController注解的类
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void pointcut() {

    }

    /**
     * 切面方法,记录日志
     *
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();//1、开始时间
        //利用RequestContextHolder获取requst对象
        ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttr.getRequest();
        String token = request.getHeader("token");
        if(token == null){
            return joinPoint.proceed();
        }
        String uri = requestAttr.getRequest().getRequestURI();
        log.info("开始计时: {}  URI: {}", new Date(), uri);
        //类名获取
        String className = joinPoint.getTarget().getClass().getName();
        //方法名获取
        String methodName = joinPoint.getSignature().getName();
        //访问目标方法的参数 可动态改变参数值
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("请求类名：{}， 请求方法：{}, 请求参数: {}", className, methodName, args);
        String clintIP = IPUtil.getIpAddr(requestAttr.getRequest());
        //可能在反向代理请求进来时，获取的IP存在不正确行 这里直接摘抄一段来自网上获取ip的代码
        log.info("请求ip：{}", clintIP);
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("暂不支持非方法注解");
        }
        long endTime = System.currentTimeMillis();
        log.info("结束计时: {},  URI: {},耗时：{}", new Date(), uri, endTime - beginTime);
        SysLog sysLog = new SysLog();
        sysLog.setIp(clintIP);
        sysLog.setMethod(methodName);
        sysLog.setOperation(className);
        sysLog.setParams(args);
        sysLog.setTime(endTime - beginTime);
        sysLogService.save(sysLog);
        return joinPoint.proceed();
    }

    /**
     * 指定拦截器规则；也可直接使用within(@org.springframework.web.bind.annotation.RestController *)
     * 这样简单点 可以通用
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowable(Throwable e) {
        log.error("切面发生了异常：", e);
        //这里可以做个统一异常处理
        //自定义一个异常 包装后排除
        //throw new AopException("xxx);
    }


}
