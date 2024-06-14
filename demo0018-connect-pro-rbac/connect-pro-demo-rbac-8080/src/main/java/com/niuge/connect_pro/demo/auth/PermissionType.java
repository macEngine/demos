package com.niuge.connect_pro.demo.auth;

public class PermissionType {
    /**
     * connect_pro - xx管理 - 某功能 - 增删查改
     * 1. 配置管理
     * 2. case管理
     * 3. comment管理
     */

    // @http(method: "POST", uri: "/case")
    public static final String case_create = "case_create";

    // @http(method: "GET", uri: "/case/{caseId}")
    public static final String case_get = "case_get";

    // @http(method: "GET", uri: "/case?participant=participant&createdFrom={createdFrom}&createdTo={createdTo}")
    public static final String case_search = "case_search";

    // @http(method: "POST", uri: "/case/{caseId}/participant")
    public static final String case_add_participant = "case_add_participant";

    // @http(method: "POST", uri: "/case/{caseId}/participant")
    public static final String case_remove_participant = "case_remove_participant";
    // @http(method: "GET", uri: "/cases/export")
    // only ops/lsp can perform this API
    public static final String case_export = "case_export";

    // @http(method: "POST", uri: "/case/{caseId}/tag")
    // @http(method: "DELETE", uri: "/case/{caseId}/tag")
    public static final String case_tag_update = "case_tag_update";

    // @http(method: "POST", uri: "/case/{caseId}/close")
    public static final String case_close = "case_close";

    // @http(method: "POST", uri: "/case/{caseId}/reopen")
    public static final String case_reopen = "case_reopen";

    // @http(method: "POST", uri: "/case/{caseId}/title")
    public static final String case_update_title = "case_update_title";

    // @http(method: "POST", uri: "/case/{caseId}/category")
    public static final String case_update_category = "case_update_category";

    // @http(method: "POST", uri: "/case/{caseId}/dimension")
    // only ops can perform this API
    public static final String case_update_dimension = "case_update_dimension";

    // @http(method: "POST", uri: "/case/{caseId}/priority")
    // only ops can perform this API
    public static final String case_update_priority = "case_update_priority";

    // @http(method: "POST", uri: "/case/{caseId}/task")
    // only ops can perform this API
    public static final String case_update_task = "case_update_task";
    
    // @http(method: "POST", uri: "/connnect/case/{caseId}/rate")
    public static final String case_rate = "case_rate";

    // @http(method: "GET", uri: "/case/{caseId}/reassignees")
    // only ops can perform this API
    public static final String case_reassignees_list = "case_reassignees_list";

    // @http(method: "PATCH", uri: "/case/{caseId}/reassignees")
    // only ops can perform this API
    public static final String case_reassign = "case_reassign";

    // @http(method: "GET", uri: "/case/upload-file-link")
    public static final String case_files_presign_url_get = "case_files_presign_url_get";

    // ============================ comment ===============================
    // @http(method: "POST", uri: "/case/{caseId}/comment")
    public static final String comment_create = "comment_create";

    // @http(method: "GET", uri: "/case/{caseId}/comments")
    // email to case feature will use this API in order to add comment history to email.
    public static final String comment_list = "comment_list";
    
    // @http(method: "POST", uri: "/case-configuration")
    // only ops can perform this API
    public static final String case_configuration_create = "case_configuration_create";

    // @http(method: "PUT", uri: "/case-configuration/{caseConfigurationId}")
    // only ops can perform this API
    public static final String case_configuration_update = "case_configuration_update";

    // @http(method: "GET", uri: "/case-configuration/{caseConfigurationId}")
    // only ops can perform this API
    public static final String case_configuration_get = "case_configuration_get";

    // @http(method: "DELETE", uri: "/case-configuration/{caseConfigurationId}")
    // only ops can perform this API
    public static final String case_configuration_delete = "case_configuration_delete";
    
    // @http(method: "GET", uri: "/case-configuration/caseConfiguration={caseConfiguration}")
    // only ops can perform this API
    public static final String case_configuration_search = "case_configuration_search";

    // @http(method: "GET", uri: "/case-configuration/case-categories")
    public static final String case_categories_list = "case_categories_list";
}
