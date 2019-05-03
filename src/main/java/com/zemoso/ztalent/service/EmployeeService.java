package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.InsertEmployeeRequest;
import com.zemoso.ztalent.controller.response.Employee;
import com.zemoso.ztalent.controller.response.InsertEmployeeResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;
import com.zemoso.ztalent.db.EmployeeRepository;
import com.zemoso.ztalent.db.EmployeeSkillRepository;
import com.zemoso.ztalent.db.SkillLookupRepository;
import com.zemoso.ztalent.db.models.EmployeeRecord;
import com.zemoso.ztalent.db.models.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    SkillLookupRepository skillLookupRepository;

    @Override
    public RetrieveEmployeesResponse getAllEmployees() {
        RetrieveEmployeesResponse retrieveEmployeesResponse = new RetrieveEmployeesResponse();
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach( employee -> {
            Employee emp = new Employee();
            emp.setId(employee.getId());
            emp.setDesignation(employee.getDesignation());
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            List<String> skills = new ArrayList<>();

            employeeSkillRepository.findSkillsByEmpId(emp.getId()).forEach( employeeSkillId -> {
                skills.add(skillLookupRepository.findTagById(employeeSkillId));
            });
            emp.setSkills(skills);
            employees.add(emp);
        });

        retrieveEmployeesResponse.setEmployees(employees);
        return retrieveEmployeesResponse;
    }

    @Override
    public InsertEmployeeResponse insertEmployee(InsertEmployeeRequest insertEmployeeRequest) {
        InsertEmployeeResponse insertEmployeeResponse = new InsertEmployeeResponse();
        EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setFirstName(insertEmployeeRequest.getFirstName());
        employeeRecord.setLastName(insertEmployeeRequest.getLastName());
        employeeRecord.setDesignation(insertEmployeeRequest.getDesignation());
        employeeRecord.setProjectAssigned(insertEmployeeRequest.isProjectAssigned());
        employeeRepository.save(employeeRecord);
        EmployeeSkill employeeSkill = new EmployeeSkill();
        // TODO
        employeeSkillRepository.save(employeeSkill);
        return insertEmployeeResponse;
    }
}
