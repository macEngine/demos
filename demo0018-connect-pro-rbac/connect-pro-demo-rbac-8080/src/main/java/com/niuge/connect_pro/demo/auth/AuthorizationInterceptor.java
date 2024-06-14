package com.niuge.connect_pro.demo.auth;

import com.niuge.connect_pro.demo.model.RequesterThreadLocal;
import com.niuge.connect_pro.demo.model.RequesterVO;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    Method method = ((HandlerMethod) handler).getMethod();
    if (!method.isAnnotationPresent(RequirePermission.class)) {
      System.out.println("No RequirePermission annotation, return true.");
      return true;
    }
    RequesterVO requesterVO = RequesterThreadLocal.get();

    RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
    if (!verifyPermission(requesterVO, requirePermission)) {
      throw new RuntimeException("Does not have required permissions, return false");
    }
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    RequesterThreadLocal.remove();
  }

  private boolean verifyPermission(RequesterVO requesterVO, RequirePermission requirePermission) {
    if (requirePermission == null) {
      System.out.println("Require permission is null, return true.");
      return true;
    }

    if (!requesterVO.getActionAndResourceMap().keySet().contains(requirePermission.value())){
         return false;
    }
    return true;
  }
}
