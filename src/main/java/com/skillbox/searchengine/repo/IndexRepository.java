package com.skillbox.searchengine.repo;

import com.skillbox.searchengine.model.entity.IndexingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends CrudRepository<IndexingEntity, Integer> {
    List<IndexingEntity> findByLemmaId(int lemmaId);

    List<IndexingEntity> findByPageId(int pageId);

    IndexingEntity findByLemmaIdAndPageId(int lemmaId, int pageId);


}

