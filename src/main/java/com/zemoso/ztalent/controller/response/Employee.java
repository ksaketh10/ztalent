package com.zemoso.ztalent.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private List<String> skills;
}
