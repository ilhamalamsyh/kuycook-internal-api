package com.kuycook.kuycookinternalapi.services.article;

import com.kuycook.kuycookinternalapi.models.Article;

public interface ArticleService {
    Article createArticle(Article article);

    Article updateArticle(Article article, Long id);

    Article deleteArticle(Article article, Long id);
}
