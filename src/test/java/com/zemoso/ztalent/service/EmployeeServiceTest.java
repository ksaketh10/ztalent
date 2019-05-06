package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.InsertEmployeeRequest;
import com.zemoso.ztalent.controller.response.InsertEmployeeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void insertEmployee_returnsInsertEmployeeResponse() {
        InsertEmployeeRequest insertEmployeeRequest = new InsertEmployeeRequest();

        insertEmployeeRequest.setDesignation("SDEIII");
        insertEmployeeRequest.setFirstName("Saketh");
        insertEmployeeRequest.setLastName("Kumar");
        insertEmployeeRequest.setSkills(Arrays.asList("React", "Java"));
        insertEmployeeRequest.setProjectAssigned(true);

        System.out.println(employeeService.insertEmployee(insertEmployeeRequest));
    }

}