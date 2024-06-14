package com.niuge.connect_pro.demo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.niuge.connect_pro.demo.authmodel.JWTTokenScope;
import com.niuge.connect_pro.demo.authmodel.Resource;
import com.niuge.connect_pro.demo.authmodel.ResourceType;
import com.niuge.connect_pro.demo.model.RequesterParty;
import com.niuge.connect_pro.demo.authmodel.Scope;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JWTTokenSigner {
    public static final Long DEFAULT_LIVE_MILLIS = 1000000L;
    public static final byte[] DEFAULT_KEY =
            "GM Connect Service default key of JWT token generation".getBytes(StandardCharsets.UTF_8);
    ;
    public static final String ISSUER = "GM Connect Service";

    public String sign(JWTTokenScope scope) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + DEFAULT_LIVE_MILLIS;
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKeySpec signingKey = new SecretKeySpec(DEFAULT_KEY, signatureAlgorithm.getJcaName());

        io.jsonwebtoken.JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject("subject")
                .setIssuer(ISSUER)
                .setExpiration(exp); // exp is configurable, default is 24 hours
        builder.claim("scope", scope);
        builder.signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public Claims verify(String jwt) {
        return Jwts.parser().setSigningKey(DEFAULT_KEY).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) {
        JWTTokenSigner jwtTokenSigner = new JWTTokenSigner();
        JWTTokenScope jwtTokenScope = new JWTTokenScope();
        jwtTokenScope.setRequesterId("wjsu");
        jwtTokenScope.setRequesterParty(RequesterParty.SHIPPER);

        List<Scope> scopes = Lists.newArrayList();
        Scope scope = new Scope();
        Resource resource = new Resource();
        resource.setResourceType(ResourceType.PARTICIPANT);
        resource.setResourceValue(Lists.newArrayList("1", "2", "3"));
        scope.setActions(Lists.newArrayList(PermissionType.case_configuration_get));
        scope.setResource(resource);

        Scope scope2 = new Scope();
        Resource resource2 = new Resource();
        resource2.setResourceType(ResourceType.OWNER);
        resource2.setResourceValue(Lists.newArrayList("admin"));
        scope2.setActions(Lists.newArrayList(PermissionType.case_configuration_create));
        scope2.setResource(resource2);


        scopes.add(scope);
        scopes.add(scope2);
        jwtTokenScope.setScopes(scopes);

        System.out.println( new Gson().toJson(scope));
        String token = jwtTokenSigner.sign(jwtTokenScope);
        System.out.println(token);


        Claims claims = jwtTokenSigner.verify(token);
        System.out.println(claims);
        System.out.println(claims.getSubject());
        System.out.println(claims.get("scope"));

        ObjectMapper objectMapper = new ObjectMapper();
        JWTTokenScope scope1 = objectMapper.convertValue(claims.get("scope"), JWTTokenScope.class);
        System.out.println(scope1);
    }
}
