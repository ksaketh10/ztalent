package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.controller.request.EmployeeRequest;
import com.zemoso.ztalent.controller.response.GenericResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;
import com.zemoso.ztalent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zemoso.ztalent.controller.exceptions.custom.*;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

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

    @PostMapping("/api/createEmployee")
    public Object createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() != null && !employeeRequest.getFirstName().isEmpty()
                && employeeRequest.getDesignation() != null && !employeeRequest.getDesignation().isEmpty()) {
            GenericResponse genericResponse;
            try {
                genericResponse =  employeeService.insertEmployee(employeeRequest);
            } catch (DuplicateEntryException e) {
                throw e;
            }
            return genericResponse;
        } else {
            throw new InvalidParametersException();
        }
    }

    @DeleteMapping("/api/delete/{id}")
    public Object deleteEmployee(@PathVariable (value = "id") Long id) {
        GenericResponse genericResponse;
        try {
            genericResponse = employeeService.deleteEmployee(id);
        } catch (NoDataFoundException e) {
            throw e;
        }
        return genericResponse;
    }

    @PutMapping("/api/update/{id}")
    public Object updateEmployee(@PathVariable (value = "id") Long id, @RequestBody EmployeeRequest employeeRequest) {
        GenericResponse genericResponse;
        if (employeeRequest.getFirstName() == null || employeeRequest.getDesignation() == null) {
            throw new InvalidParametersException();
        } else {
            try {
                genericResponse = employeeService.updateRecord(id, employeeRequest);
            } catch (NoDataFoundException e) {
                throw e;
            }
        }
        return genericResponse;
    }
}
