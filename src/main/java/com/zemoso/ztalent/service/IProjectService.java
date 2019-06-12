package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.Project;

import java.util.List;

public interface IProjectService {

    List<String> getAllProjects();

    void insertProject(Project project, Long userId);

    void deleteProject(Long id);
}
