package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.models.Project;
import com.zemoso.ztalent.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProjectService projectService;

    @GetMapping("/project")
    public Object getProjects() {
        try  {
            return projectService.getAllProjects();
        } catch (Exception e) {
            LOGGER.error("getProjects "+ e.getMessage());
            throw e;
        }
    }

    @PostMapping("/project")
    public Object insertProject(@RequestBody Project project, @RequestHeader("user") String user) {
        try  {
            projectService.insertProject(project, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("insertProject "+ e.getMessage());
            throw e;
        }
    }

    @PutMapping("/project/update/{id}")
    public Object updateProject(@PathVariable (value = "id") Long id, @RequestHeader("user") String user, @RequestBody Project project) {
        try  {
            projectService.updateProject(id, project, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("updateProject "+ e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/project/delete/{id}")
    public Object deleteProject(@PathVariable (value = "id") Long id) {
        try  {
            projectService.deleteProject(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("deleteProject "+ e.getMessage());
            throw e;
        }
    }
}
