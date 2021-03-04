package com.chintpower.DingTalk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

/**
 * 			"ext":"{\"faceCount\":\"19\"}",
 * 			"createDeptGroup":true,
 * 			"name":"财务信息管理部",
 * 			"id":163034128,
 * 			"autoAddUser":true,
 * 			"parentid":1
 */

@Repository
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private String name;
    private String id;
    private String parentId;
}
