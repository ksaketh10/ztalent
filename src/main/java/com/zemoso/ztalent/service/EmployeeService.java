package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.controller.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.EmployeeRepository;
import com.zemoso.ztalent.db.ProjectRepository;
import com.zemoso.ztalent.db.SkillRepository;
import com.zemoso.ztalent.models.Employee;
import com.zemoso.ztalent.models.Project;
import com.zemoso.ztalent.models.Skill;
import com.zemoso.ztalent.payload.EmployeePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    ProjectRepository projectRepository;

    // Retrive all employee information and their skills
    @Override
    public List<EmployeePayload> getAllEmployees() {
        List<EmployeePayload> employees = new ArrayList<>();

        employeeRepository.findAll().forEach(employee -> {
            EmployeePayload employeePayload = new EmployeePayload();
            employeePayload.setDesignation(employee.getDesignation());
            employeePayload.setEmpId(employee.getEmpId());
            employeePayload.setFirstName(employee.getFirstName());
            employeePayload.setLastName(employee.getLastName());
            employeePayload.setProjectAssigned(employee.getProjectAssigned());
            employeePayload.setId(employee.getId());

            Set<String> skills = new HashSet<>();
            employee.getSkills().forEach(skill -> skills.add(skill.getTag()));
            employeePayload.setSkills(skills);

            Set<String> projects = new HashSet<>();
            employee.getProjects().forEach(project -> projects.add(project.getTitle()));
            employeePayload.setProjects(projects);
            employees.add(employeePayload);
        });

        if (employees.size() == 0) throw new NoDataFoundException();
        return employees;
    }

    //Insert new EmployeePayload record with given information
    @Override
    public void insertEmployee(EmployeePayload employeePayload, String user) {
        Long empId = employeeRepository.getIdByEmpId(employeePayload.getEmpId());
        if ( empId != null) {
            throw new DuplicateEntryException();
        } else {
            Employee employee = new Employee();
            employee.setCreatedBy(user);
            employee.setUpdatedBy(user);
            saveEmployee(employeePayload, employee);
        }
    }

    //Delete EmployeePayload record based on id
    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NoDataFoundException::new);
        employeeRepository.delete(employee);
    }

    //Update EmployeePayload Record based on id
    @Override
    public void updateRecord(Long id, EmployeePayload employeePayload, String user) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NoDataFoundException::new);
        employee.setUpdatedBy(user);
        saveEmployee(employeePayload, employee);
    }

    private void saveEmployee(EmployeePayload employeePayload, Employee employee) {
        employee.setFirstName(employeePayload.getFirstName().trim());
        employee.setLastName(employeePayload.getLastName().trim());
        employee.setDesignation(employeePayload.getDesignation().trim());
        employee.setEmpId(employeePayload.getEmpId());
        employee.setProjectAssigned(employeePayload.getProjectAssigned());

        Set<Skill> skills = new HashSet<>();
        employeePayload.getSkills().forEach(skill -> {
            Long skillId = skillRepository.findIdByTag(skill.trim());
            Skill skill1 = skillRepository.findById(skillId).orElseThrow(NoDataFoundException::new);
            skills.add(skill1);
        });
        employee.setSkills(skills);

        Set<Project> projects = new HashSet<>();
        employeePayload.getProjects().forEach(project -> {
            Long projectId = projectRepository.findIdByProject(project.trim());
            Project project1 = projectRepository.findById(projectId).orElseThrow(NoDataFoundException::new);
            projects.add(project1);
        });
        employee.setProjects(projects);

        employeeRepository.save(employee);
    }
}
