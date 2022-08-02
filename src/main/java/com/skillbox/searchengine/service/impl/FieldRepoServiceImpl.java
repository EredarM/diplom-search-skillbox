package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.entity.FieldEntity;
import com.skillbox.searchengine.repo.FieldRepository;
import com.skillbox.searchengine.service.FieldRepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldRepoServiceImpl implements FieldRepositoryService {
    private final FieldRepository fieldRepository;

    public FieldRepoServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public FieldEntity getFieldByName(String fieldName) {
        return fieldRepository.findByName(fieldName);
    }

    @Override
    public synchronized void save(FieldEntity field) {
        fieldRepository.save(field);
    }

    @Override
    public List<FieldEntity> getAllField() {
        List<FieldEntity> list = new ArrayList<>();
        fieldRepository.findAll().forEach(list::add);
        return list;
    }
}
