package com.zemoso.ztalent.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private String designation;
    private List<String> skills;
    private boolean projectAssigned;
}
