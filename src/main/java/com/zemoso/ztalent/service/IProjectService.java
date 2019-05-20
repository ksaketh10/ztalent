package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.Project;

import java.util.List;

public interface IProjectService {

    List<String> getAllProjects();

    void insertProject(Project project);

    void updateProject(Long id, Project project);

    void deleteProject(Long id);
}
