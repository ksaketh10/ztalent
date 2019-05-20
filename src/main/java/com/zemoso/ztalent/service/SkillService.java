package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.controller.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.SkillRepository;
import com.zemoso.ztalent.models.Employee;
import com.zemoso.ztalent.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService implements ISkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public List<String> getAllSkills() {
        List<String> skills = new ArrayList<>();

        skillRepository.findAll().forEach(skill -> skills.add(skill.getTag()));
        if (skills.isEmpty()) throw new NoDataFoundException();
        return skills;
    }

    @Override
    public void insertSkill(Skill skill) {
        if (skillRepository.findIdByTag(skill.getTag().trim()) != null) throw new DuplicateEntryException();
        skillRepository.save(skill);
    }

    @Override
    public void updateSkill(Long id, Skill skill) {
        Skill skill1 = skillRepository.findById(id).orElseThrow(NoDataFoundException::new);
        skill1.setTag(skill.getTag());
        skillRepository.save(skill1);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow(NoDataFoundException::new);
        skillRepository.delete(skill);
    }
}
