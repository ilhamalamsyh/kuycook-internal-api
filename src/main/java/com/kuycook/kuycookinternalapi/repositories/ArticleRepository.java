package com.kuycook.kuycookinternalapi.repositories;

import com.kuycook.kuycookinternalapi.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Page<Article> findArticleByTitleContainsAndDeletedAtIsNull(String title, Pageable pageable);

    Article findArticleByIdAndDeletedAtIsNull(Long id);

    Optional<Article> findByIdAndDeletedAtIsNull(Long id);
}
