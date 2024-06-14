package com.niuge.connect_pro.demo.aop;

import com.google.common.collect.Lists;
import com.niuge.connect_pro.demo.CaseService;
import com.niuge.connect_pro.demo.auth.RequirePermission;
import com.niuge.connect_pro.demo.model.CaseVO;
import com.niuge.connect_pro.demo.authmodel.Resource;
import com.niuge.connect_pro.demo.authmodel.ResourceType;
import com.niuge.connect_pro.demo.model.RequesterThreadLocal;
import com.niuge.connect_pro.demo.model.RequesterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * aop校验当前访问case是否属于当前用户(或者scope允许的用户)
 */
@Slf4j
@Component
@Aspect
public class CasePermissionCheckAspect {
    CaseService caseService;

    @Pointcut("@annotation(com.niuge.connect_pro.demo.aop.CasePermissionCheck)")
    public void casePermissionCheckPoint() {
    }

    @Before("casePermissionCheckPoint()")
    public void doCheck(JoinPoint joinPoint) throws IllegalAccessException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);

        Parameter[] parameters = method.getParameters();
        Object[] dataArray = joinPoint.getArgs();

        // 获取Controller中的参数
        String caseId = obtainCaseId(parameters, dataArray);

        CaseVO caseVO = caseService.fetchById(caseId);
        if (null == caseVO) {
            throw new RuntimeException("case not exist");
        }
        RequesterVO requesterVO = RequesterThreadLocal.get();
        Resource resource = requesterVO.getActionAndResourceMap().get(requirePermission.value());
        if (resource.getResourceType() == ResourceType.OWNER) {
            if (!resource.getResourceValue().contains(caseVO.assignee)) {
                throw new RuntimeException("no permission");
            }
        } else if (resource.getResourceType() == ResourceType.PARTICIPANT) {
            boolean found = false;
            for (String participant : resource.getResourceValue()) {
                if (caseVO.participants.contains(participant)) {
                    found = true;
                }
            }
            if (!found) {
                throw new RuntimeException("no permission");
            }
        }
    }
    
    private String obtainCaseId(Parameter[] parameters, Object[] dataArray) throws IllegalAccessException {
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object data = dataArray[i];
            if (parameter.isAnnotationPresent(CaseId.class)) {
                return (String) data;
            }

            if (data != null) {
                for (Field field : FieldUtils.getAllFields(data.getClass())) {
                    if (field.isAnnotationPresent(CaseId.class)) {
                        return (String) data;
                    }
                }
            }
        }
        return null;
    }
}
