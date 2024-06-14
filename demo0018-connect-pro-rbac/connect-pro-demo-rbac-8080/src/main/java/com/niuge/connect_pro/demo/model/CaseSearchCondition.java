package com.niuge.connect_pro.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class CaseSearchCondition {
    public String caseCategory;
    public List<String> participants;
}
