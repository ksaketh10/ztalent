package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.request.EmployeeRequest;
import com.zemoso.ztalent.controller.response.Employee;
import com.zemoso.ztalent.controller.response.GenericResponse;
import com.zemoso.ztalent.controller.response.RetrieveEmployeesResponse;
import com.zemoso.ztalent.db.EmployeeRecordRepository;
import com.zemoso.ztalent.db.SkillLookupRepository;
import com.zemoso.ztalent.db.models.EmployeeRecord;
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

    // Retrive all employee information and their skills
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
            employee.getSkillLookups().forEach(skillLookup -> skills.add(skillLookup.getTag()));
            emp.setSkills(skills);

            employees.add(emp);
        });

        if (employees.size() == 0) throw new NoDataFoundException();
        retrieveEmployeesResponse.setEmployees(employees);
        return retrieveEmployeesResponse;
    }

    //Insert new Employee record with given information
    @Override
    public GenericResponse insertEmployee(EmployeeRequest employeeRequest) {
        GenericResponse genericResponse = new GenericResponse();
        String firstName = employeeRequest.getFirstName().trim();
        String lastName = employeeRequest.getLastName().trim();
        Long empId = employeeRecordRepository.getIdByFirstNameAndLastName(firstName, lastName);
        if ( empId != null) {
            throw new DuplicateEntryException();
        } else {
            EmployeeRecord employeeRecord = new EmployeeRecord();
            saveEmployeeRecord(employeeRequest, employeeRecord);
            genericResponse.setStatus("Record Created");
            return genericResponse;
        }
    }

    //Delete Employee record based on id
    @Override
    public GenericResponse deleteEmployee(Long id) {
        GenericResponse genericResponse = new GenericResponse();
        EmployeeRecord employeeRecord = employeeRecordRepository.findById(id).orElseThrow(NoDataFoundException::new);
        employeeRecordRepository.delete(employeeRecord);
        genericResponse.setStatus("Record deleted");
        return genericResponse;
    }

    //Update Employee Record based on id
    @Override
    public GenericResponse updateRecord(Long id, EmployeeRequest employeeRequest) {
        GenericResponse genericResponse = new GenericResponse();
        EmployeeRecord employeeRecord = employeeRecordRepository.findById(id).orElseThrow(NoDataFoundException::new);
        saveEmployeeRecord(employeeRequest, employeeRecord);
        genericResponse.setStatus("Record Updated");
        return genericResponse;
    }

    //Gets new employee request from API and employee record from DB and updates DB
    private void saveEmployeeRecord(EmployeeRequest employeeRequest, EmployeeRecord employeeRecord) {
        employeeRecord.setProjectAssigned(employeeRequest.isProjectAssigned());
        employeeRecord.setFirstName(employeeRequest.getFirstName().trim());
        employeeRecord.setLastName(employeeRequest.getLastName().trim());
        employeeRecord.setDesignation(employeeRequest.getDesignation().trim());
        Set<SkillLookup> skillLookups = new HashSet<>();

        employeeRequest.getSkills().forEach(skill -> {
            Long skillId = skillLookupRepository.findIdByTag(skill.trim());
            if (skillId == null) {
                SkillLookup skillLookup = new SkillLookup();
                skillLookup.setTag(skill.trim());
                skillLookups.add(skillLookup);
            } else {
                skillLookupRepository.findById(skillId).ifPresent(skillLookups::add);
            }
        });
        employeeRecord.setSkillLookups(skillLookups);
        employeeRecordRepository.save(employeeRecord);
    }
}
