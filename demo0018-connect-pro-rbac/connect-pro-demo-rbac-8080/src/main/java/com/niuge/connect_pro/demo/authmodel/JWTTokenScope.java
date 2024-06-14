package com.niuge.connect_pro.demo.authmodel;

import com.niuge.connect_pro.demo.model.RequesterParty;
import lombok.Data;

import java.util.List;

@Data
public class JWTTokenScope {
    String requesterId;
    RequesterParty requesterParty;
    List<Scope> scopes;
}
