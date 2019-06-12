package com.zemoso.ztalent.service;

import com.zemoso.ztalent.db.UserRepository;
import com.zemoso.ztalent.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.SkillRepository;
import com.zemoso.ztalent.models.Skill;
import com.zemoso.ztalent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService implements ISkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<String> getAllSkills() {
        List<String> skills = new ArrayList<>();

        skillRepository.findAll().forEach(skill -> skills.add(skill.getTag()));
        if (skills.isEmpty()) throw new NoDataFoundException();
        return skills;
    }

    @Override
    public void insertSkill(Skill skill, Long userId) {
        Long id = skillRepository.findIdByTag(skill.getTag().trim());
        if (id != null) throw new DuplicateEntryException();
        skill.setCreatedBy(getEmailByUserId(userId));
        skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow(NoDataFoundException::new);
        skillRepository.delete(skill);
    }

    private String getEmailByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());
        return user.getEmail();
    }
}
