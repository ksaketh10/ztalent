package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.models.Skill;
import com.zemoso.ztalent.models.User;
import com.zemoso.ztalent.security.TokenProvider;
import com.zemoso.ztalent.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SkillController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SkillService skillService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/skill")
    public Object getSkills() {
        try  {
            return skillService.getAllSkills();
        } catch (Exception e) {
            LOGGER.error("getSkills "+ e.getMessage());
            throw e;
        }
    }

    @PostMapping("/skill")
    public Object insertSkill( @RequestHeader("Authorization") String token, @RequestBody Skill skill) {
        try  {
            Long userId = tokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            skillService.insertSkill(skill, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("insertSkill "+ e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/skill/delete/{id}")
    public Object deleteSkill(@PathVariable (value = "id") Long id) {
        try  {
            skillService.deleteSkill(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("deleteSkill "+ e.getMessage());
            throw e;
        }
    }
}
