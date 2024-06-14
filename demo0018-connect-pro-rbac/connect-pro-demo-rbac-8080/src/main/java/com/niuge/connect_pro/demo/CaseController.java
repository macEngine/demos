package com.niuge.connect_pro.demo;

import com.niuge.connect_pro.demo.auth.PermissionType;
import com.niuge.connect_pro.demo.auth.RequirePermission;
import com.niuge.connect_pro.demo.model.CaseSearchCondition;
import com.niuge.connect_pro.demo.authmodel.Resource;
import com.niuge.connect_pro.demo.authmodel.ResourceType;
import com.niuge.connect_pro.demo.model.RequesterThreadLocal;
import com.niuge.connect_pro.demo.model.RequesterVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class CaseController {
    @GetMapping(value = "/case")
    @RequirePermission(value = PermissionType.case_search)
    public String search(@RequestParam("category") String category) {
        RequesterVO requesterVO = RequesterThreadLocal.get();
        Resource resource = requesterVO.getActionAndResourceMap().get(PermissionType.case_search);
        CaseSearchCondition caseSearchCondition = new CaseSearchCondition();
        caseSearchCondition.setCaseCategory(category);
        if (resource.getResourceType() == ResourceType.PARTICIPANT) {
            caseSearchCondition.setParticipants(resource.getResourceValue());
        }
        // caseSearch.doSearch(caseSearchCondition);
        return "search success, category=" + category;
    }

}
