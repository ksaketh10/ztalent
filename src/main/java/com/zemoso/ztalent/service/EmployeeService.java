package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.InsertEmployeeRequest;
import com.zemoso.ztalent.controller.response.Employee;
import com.zemoso.ztalent.controller.response.InsertEmployeeResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;
import com.zemoso.ztalent.db.EmployeeRecordRepository;
import com.zemoso.ztalent.db.EmployeeSkillRepository;
import com.zemoso.ztalent.db.SkillLookupRepository;
import com.zemoso.ztalent.db.models.EmployeeRecord;
import com.zemoso.ztalent.db.models.EmployeeSkill;
import com.zemoso.ztalent.db.models.SkillLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zemoso.ztalent.controller.exceptions.custom.*;

import java.util.*;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRecordRepository employeeRecordRepository;

    @Autowired
    SkillLookupRepository skillLookupRepository;

    @Autowired
    EmployeeSkillRepository employeeSkillRepository;

    @Override
    public RetrieveEmployeesResponse getAllEmployees() {
        RetrieveEmployeesResponse retrieveEmployeesResponse = new RetrieveEmployeesResponse();
        List<Employee> employees = new ArrayList<>();

        employeeRecordRepository.findAll().forEach(employee -> {
            Employee emp = new Employee();
            emp.setId(employee.getId());
            emp.setDesignation(employee.getDesignation());
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            List<String> skills = new ArrayList<>();

            employeeSkillRepository.findEmployeeSkills(employee.getId()).forEach(employeeSkill ->
                                    skillLookupRepository.findById(employeeSkill.getSkillLookup().getId())
                                    .ifPresent(skillLookup -> skills.add(skillLookup.getTag())));
            emp.setSkills(skills);
            employees.add(emp);
        });

        retrieveEmployeesResponse.setEmployees(employees);
        if (employees.size() == 0) throw new NoDataFoundException();
        return retrieveEmployeesResponse;
    }

    @Override
    public InsertEmployeeResponse insertEmployee(InsertEmployeeRequest insertEmployeeRequest) {
        InsertEmployeeResponse insertEmployeeResponse = new InsertEmployeeResponse();
        String firstName = insertEmployeeRequest.getFirstName();
        String lastName = insertEmployeeRequest.getLastName();
        Long empId = employeeRecordRepository.getIdByFirstNameAndLastName(firstName, lastName);
        if ( empId != null) {
            throw new DuplicateEntryException();
        } else {
            EmployeeRecord employeeRecord = new EmployeeRecord();
            employeeRecord.setFirstName(insertEmployeeRequest.getFirstName());
            employeeRecord.setLastName(insertEmployeeRequest.getLastName());
            employeeRecord.setDesignation(insertEmployeeRequest.getDesignation());
            employeeRecord.setProjectAssigned(insertEmployeeRequest.isProjectAssigned());
            employeeRecordRepository.save(employeeRecord);
            empId = employeeRecordRepository.getIdByFirstNameAndLastName(firstName, lastName);
            employeeRecord.setId(empId);

            insertEmployeeRequest.getSkills().forEach(skill -> {
                Long skillId = skillLookupRepository.findIdByTag(skill);
                SkillLookup skillLookup = new SkillLookup();
                skillLookup.setTag(skill);
                if (skillId == null) {
                    skillLookupRepository.save(skillLookup);
                    skillId = skillLookupRepository.findIdByTag(skill);
                }
                skillLookup.setId(skillId);
                EmployeeSkill employeeSkill = new EmployeeSkill();
                employeeSkill.setSkillLookup(skillLookup);
                employeeSkill.setEmployeeRecord(employeeRecord);
                employeeSkillRepository.save(employeeSkill);
            });
            return insertEmployeeResponse;
        }
    }
}
