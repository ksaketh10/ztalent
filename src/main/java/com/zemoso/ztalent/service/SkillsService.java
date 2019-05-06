package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.exceptions.custom.NoDataFoundException;
import com.zemoso.ztalent.db.SkillLookupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillsService implements ISkillsService {

    @Autowired
    SkillLookupRepository skillLookupRepository;

    @Override
    public List<String> getAllSkills() {
        List<String> skills = new ArrayList<>();

        skillLookupRepository.findAll().forEach(skillLookup -> {
            skills.add(skillLookup.getTag());
        });
        if (skills.isEmpty()) throw new NoDataFoundException();
        return skills;
    }
}
