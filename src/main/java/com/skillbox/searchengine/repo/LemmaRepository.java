package com.skillbox.searchengine.repo;

import com.skillbox.searchengine.model.entity.LemmaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LemmaRepository extends CrudRepository<LemmaEntity, Integer> {
    List<LemmaEntity> findByLemma(String lemma);

    @Query(value = "SELECT * from search_lemma WHERE id IN(:id)", nativeQuery = true)
    List<LemmaEntity> findById(int[] id);

    @Query(value = "SELECT count(*) from LemmaEntity where site_id = :id")
    long count(@Param("id") long id);
}