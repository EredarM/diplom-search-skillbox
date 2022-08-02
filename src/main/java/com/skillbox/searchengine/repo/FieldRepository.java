package com.skillbox.searchengine.repo;

import com.skillbox.searchengine.model.entity.FieldEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends CrudRepository<FieldEntity, Integer> {
    FieldEntity findByName(String name);
}