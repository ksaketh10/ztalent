package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.Project;

import java.util.List;

public interface IProjectService {

    List<String> getAllProjects();

    void insertProject(Project project, String user);

    void updateProject(Long id, Project project, String user);

    void deleteProject(Long id);
}
