package com.zemoso.ztalent.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RetrieveEmployeesResponse {

    private List<Employee> employees;
}
