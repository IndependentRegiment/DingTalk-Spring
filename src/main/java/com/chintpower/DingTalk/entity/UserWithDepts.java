package com.chintpower.DingTalk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithDepts {
    private String userId;
    private String name;
    private ArrayList<String> depIdList;
    private String employStatus;
}
