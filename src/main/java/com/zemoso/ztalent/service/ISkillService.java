package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.Skill;

import java.util.List;

public interface ISkillService {

    List<String> getAllSkills();

    void insertSkill(Skill skill, String user);

    void updateSkill(Long id, Skill skill, String user);

    void deleteSkill(Long id);
}
