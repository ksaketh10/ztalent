package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.Skill;

import java.util.List;

public interface ISkillService {

    List<String> getAllSkills();

    void insertSkill(Skill skill);

    void updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);
}
