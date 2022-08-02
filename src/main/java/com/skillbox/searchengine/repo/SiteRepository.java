package com.skillbox.searchengine.repo;

import com.skillbox.searchengine.model.entity.SiteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends CrudRepository<SiteEntity, Integer> {
    SiteEntity findByUrl(String url);
}
