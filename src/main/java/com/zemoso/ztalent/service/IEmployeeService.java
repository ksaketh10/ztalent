package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.InsertEmployeeRequest;
import com.zemoso.ztalent.controller.response.InsertEmployeeResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;

public interface IEmployeeService {

    RetrieveEmployeesResponse getAllEmployees();

    InsertEmployeeResponse insertEmployee(InsertEmployeeRequest insertEmployeeRequest);
}
