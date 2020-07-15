package com.zemoso.ztalent.service;

import com.zemoso.ztalent.db.UserRepository;
import com.zemoso.ztalent.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.ProjectRepository;
import com.zemoso.ztalent.models.Project;
import com.zemoso.ztalent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    //Get all available projects
    @Override
    public List<String> getAllProjects() {
        List<String> projects = new ArrayList<>();

        projectRepository.findAll().forEach(project -> projects.add(project.getTitle()));
        if (projects.isEmpty()) throw new NoDataFoundException();
        return projects;
    }

    @Override
    public void insertProject(Project project, Long userId) {
        Long id = projectRepository.findIdByProject(project.getTitle().trim());
        if (id!= null) throw new DuplicateEntryException();
        project.setCreatedBy(getEmailByUserId(userId));
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(NoDataFoundException::new);
        projectRepository.delete(project);
    }

    private String getEmailByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());
        return user.getEmail();
    }
}
