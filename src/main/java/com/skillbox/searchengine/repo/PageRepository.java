package com.skillbox.searchengine.repo;

import com.skillbox.searchengine.model.entity.PageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends CrudRepository<PageEntity, Integer> {
    PageEntity findByPath(String path);

    Optional<PageEntity> findByIdAndSiteId(int id, int siteId);

    @Query(value = "SELECT count(*) from PageEntity where site_id = :id")
    long count(@Param("id") long id);
}