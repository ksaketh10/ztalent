package com.zemoso.ztalent.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployeePayload implements Serializable {

    private Long id;
    
    private Long empId;

    private String firstName;

    private String lastName;

    private String designation;

    private Boolean projectAssigned;

    private Set<String> projects = new HashSet<>();

    private Set<String> skills = new HashSet<>();
}
