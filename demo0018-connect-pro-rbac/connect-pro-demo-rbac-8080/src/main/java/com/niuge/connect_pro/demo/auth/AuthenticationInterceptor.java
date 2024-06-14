package com.niuge.connect_pro.demo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.niuge.connect_pro.demo.authmodel.JWTTokenScope;
import com.niuge.connect_pro.demo.authmodel.Resource;
import com.niuge.connect_pro.demo.authmodel.Scope;
import com.niuge.connect_pro.demo.model.RequesterThreadLocal;
import com.niuge.connect_pro.demo.model.RequesterVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JWTTokenSigner jwtTokenSigner;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtToken = RequestParser.getJWTToken(request);
        if (jwtToken == null) {
            throw new RuntimeException("");
        }

        verifyLoginStatus(jwtToken);
        RequesterVO requesterVO = parseRequester(jwtToken);

        RequesterThreadLocal.set(requesterVO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequesterThreadLocal.set(null);
    }

    private void verifyLoginStatus(String userToken) {
        try {
            Claims claim = jwtTokenSigner.verify(userToken);
            System.out.println(claim);
        } catch (Exception e) {
            throw new RuntimeException("Token is invalid.");
        }
    }

    private RequesterVO parseRequester(String userToken) {
        Claims claims = jwtTokenSigner.verify(userToken);

        ObjectMapper objectMapper = new ObjectMapper();
        JWTTokenScope scope = objectMapper.convertValue(claims.get("scope"), JWTTokenScope.class);
        RequesterVO requesterVO = new RequesterVO();
        requesterVO.setRequesterId(scope.getRequesterId());
        requesterVO.setRequesterParty(scope.getRequesterParty());

        Map<String, Resource> map = Maps.newConcurrentMap();
        for (int i = 0; i < scope.getScopes().size(); ++i) {
            Scope permissionScope = scope.getScopes().get(i);
            List<String> permissionTypes = permissionScope.getActions();
            for (String permissionType : permissionTypes) {
                map.put(permissionType, permissionScope.getResource());
            }
        }

        requesterVO.setActionAndResourceMap(map);
        System.out.println(map);
        return requesterVO;
    }
}
