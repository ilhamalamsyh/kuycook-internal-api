package com.kuycook.kuycookinternalapi.services.article;

import com.kuycook.kuycookinternalapi.models.Article;
import com.kuycook.kuycookinternalapi.repositories.ArticleRepository;
import com.kuycook.kuycookinternalapi.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;

@Service
@TransactionScoped
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        super();
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article, Long id) {
        Article existingArticle = articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Article", "ID", id));

        existingArticle.setTitle(article.getTitle());
        existingArticle.setDescription(article.getDescription());
        existingArticle.setUpdatedAt(article.getUpdatedAt());
        articleRepository.save(existingArticle);
        return existingArticle;
    }

    @Override
    public Article deleteArticle(Article article, Long id) {
        Article existingArticle = articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Article", "ID", id));
        existingArticle.setDeletedAt(article.getDeletedAt());
        articleRepository.save(existingArticle);
        return existingArticle;
    }

    public Iterable<Article> getAllArticle(String title, Pageable pageable) {
        return articleRepository.findArticleByTitleContainsAndDeletedAtIsNull(title, pageable);
    }

    public Article getArticleDetail(Long id) {
        return articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Article", "ID", id));
    }
}
