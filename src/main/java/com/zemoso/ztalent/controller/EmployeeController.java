package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.controller.request.InsertEmployeeRequest;
import com.zemoso.ztalent.controller.response.InsertEmployeeResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;
import com.zemoso.ztalent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zemoso.ztalent.controller.exceptions.custom.*;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/api/createEmployee")
    public Object createEmployee(@RequestBody InsertEmployeeRequest insertEmployeeRequest) {
        if (insertEmployeeRequest.getFirstName() == null && insertEmployeeRequest.getDesignation() ==null) {
            throw new InvalidParametersException();
        } else {
            InsertEmployeeResponse insertEmployeeResponse;
            try {
                insertEmployeeResponse =  employeeService.insertEmployee(insertEmployeeRequest);
            } catch (DuplicateEntryException e) {
                throw e;
            }
            return insertEmployeeResponse;
        }
    }

    @GetMapping("/api/getAllEmployees")
    public Object getAllEMployees() {
        RetrieveEmployeesResponse retrieveEmployeesResponse;
        try {
            retrieveEmployeesResponse = employeeService.getAllEmployees();
        }  catch (NoDataFoundException e) {
            throw e;
        }
        return retrieveEmployeesResponse;
    }
}
