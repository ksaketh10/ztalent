package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.controller.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.ProjectRepository;
import com.zemoso.ztalent.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    ProjectRepository projectRepository;

    //Get all available projects
    @Override
    public List<String> getAllProjects() {
        List<String> projects = new ArrayList<>();

        projectRepository.findAll().forEach(project -> projects.add(project.getTitle()));
        if (projects.isEmpty()) throw new NoDataFoundException();
        return projects;
    }

    @Override
    public void insertProject(Project project, String user) {
        Long id = projectRepository.findIdByProject(project.getTitle().trim());
        if (id!= null) throw new DuplicateEntryException();
        project.setCreatedBy(user);
        projectRepository.save(project);
    }

    @Override
    public void updateProject(Long id, Project project, String user) {
        Project project1 = projectRepository.findById(id).orElseThrow(NoDataFoundException::new);
        project1.setTitle(project.getTitle());
        project1.setUpdatedBy(user);
        projectRepository.save(project1);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(NoDataFoundException::new);
        projectRepository.delete(project);
    }
}
