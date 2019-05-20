package com.zemoso.ztalent.service;

import com.zemoso.ztalent.payload.EmployeePayload;

import java.util.List;

public interface IEmployeeService {

    List<EmployeePayload> getAllEmployees();

    void insertEmployee(EmployeePayload employee);

    void deleteEmployee(Long id);

    void updateRecord(Long id, EmployeePayload employee);
}
