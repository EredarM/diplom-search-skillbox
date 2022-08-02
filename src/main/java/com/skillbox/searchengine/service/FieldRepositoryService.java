package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.FieldEntity;

import java.util.List;

public interface FieldRepositoryService {
    FieldEntity getFieldByName(String fieldName);

    void save(FieldEntity field);

    List<FieldEntity> getAllField();


}
