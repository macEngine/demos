package com.niuge.connect_pro.demo;

import com.niuge.connect_pro.demo.auth.PermissionType;
import com.niuge.connect_pro.demo.auth.RequirePermission;
import com.niuge.connect_pro.demo.model.CreateCaseConfigurationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/case-configuration")
public class CaseConfigurationController {
    @GetMapping(value = "/{caseConfigurationId}")
    @RequirePermission(value = PermissionType.case_configuration_get)
    public String getCaseConfiguration(@PathVariable String caseConfigurationId) {
        return "getCaseConfiguration success, caseConfigurationId=" + caseConfigurationId;

    }

    @PostMapping(value = "/")
    public String login(@RequestBody CreateCaseConfigurationRequest request) {
        return "case configuration create successfully.";
    }
}
