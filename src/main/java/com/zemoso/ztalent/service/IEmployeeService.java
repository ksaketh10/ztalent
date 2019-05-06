package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.EmployeeRequest;
import com.zemoso.ztalent.controller.response.GenericResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;

public interface IEmployeeService {

    RetrieveEmployeesResponse getAllEmployees();

    GenericResponse insertEmployee(EmployeeRequest employeeRequest);

    GenericResponse deleteEmployee(Long id);

    GenericResponse updateRecord(Long id, EmployeeRequest employeeRequest);
}
