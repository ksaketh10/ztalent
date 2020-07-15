package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.models.Project;
import com.zemoso.ztalent.security.TokenProvider;
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

    @Autowired
    private TokenProvider tokenProvider;

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
    public Object insertProject(@RequestBody Project project, @RequestHeader("Authorization") String token) {
        try  {
            Long userId = tokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            projectService.insertProject(project, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("insertProject "+ e.getMessage());
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
