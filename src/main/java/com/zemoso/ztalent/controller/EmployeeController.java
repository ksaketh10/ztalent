package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.controller.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.controller.exceptions.custom.InvalidParametersException;
import com.zemoso.ztalent.controller.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.payload.EmployeePayload;
import com.zemoso.ztalent.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employee")
    public Object getAllEmployees() {
        try {
            return employeeService.getAllEmployees();
        }  catch (NoDataFoundException e) {
            LOGGER.error("getAllEmployees" + e.getMessage());
            throw e;
        }
    }

    @PostMapping("/employee")
    public Object createEmployee(@RequestBody EmployeePayload employee, @RequestHeader("user") String user) {
        if (checkValidPayload(employee)) {
            try {
                employeeService.insertEmployee(employee, user);
                return ResponseEntity.ok().build();
            } catch (DuplicateEntryException e) {
                LOGGER.error("createEmployee" + e.getMessage());
                throw e;
            }
        } else {
            LOGGER.error("createEmployee => Invalid Parameters");
            throw new InvalidParametersException();
        }
    }

    @DeleteMapping("/employee/{id}")
    public Object deleteEmployee(@PathVariable (value = "id") Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (NoDataFoundException e) {
            LOGGER.error("deleteEmployee " + e.getMessage());
            throw e;
        }
    }

    @PutMapping("/employee/{id}")
    public Object updateEmployee(@PathVariable (value = "id") Long id, @RequestHeader("user") String user, @RequestBody EmployeePayload employee) {
        if (!checkValidPayload(employee)) {
            LOGGER.error("updateEmployee => Invalid Parameters");
            throw new InvalidParametersException();
        } else {
            try {
                employeeService.updateRecord(id, employee, user);
                return ResponseEntity.ok().build();
            } catch (NoDataFoundException e) {
                LOGGER.error("updateEmployee " + e.getMessage());
                throw e;
            }
        }
    }

    private boolean checkValidPayload(EmployeePayload employee) {
        return employee.getFirstName() != null && !employee.getFirstName().isEmpty()
                && employee.getDesignation() != null && !employee.getDesignation().isEmpty()
                && employee.getEmpId() != null;
    }
}
