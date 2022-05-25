package com.kuycook.kuycookinternalapi.controllers;

import com.kuycook.kuycookinternalapi.dto.requests.ArticleRequest;
import com.kuycook.kuycookinternalapi.dto.requests.SearchDataRequest;
import com.kuycook.kuycookinternalapi.models.Article;
import com.kuycook.kuycookinternalapi.repositories.ArticleRepository;
import com.kuycook.kuycookinternalapi.services.article.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(maxAge = 3600, origins = "*")
@RequestMapping("/kuycook/api/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleServiceImpl articleServiceImpl;

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Article> findAllArticle(@RequestBody SearchDataRequest dataRequest,
                                            @PathVariable("size") int size, @PathVariable("page") int page,
                                            @PathVariable("sort") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (sort.equalsIgnoreCase("asc"))
            pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return articleServiceImpl.getAllArticle(dataRequest.getSearchKey(), pageable);
    }

    @PostMapping("/search/{size}/{page}")
    public Iterable<Article> findAllArticle(@RequestBody SearchDataRequest dataRequest,
                                            @PathVariable("size") int size, @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return articleServiceImpl.getAllArticle(dataRequest.getSearchKey(), pageable);
    }

    @GetMapping("/{id}")
    public Article getArticleDetail(@PathVariable("id") Long id) {
        return articleServiceImpl.getArticleDetail(id);
    }

    @PostMapping()
    public Article createArticle(@RequestBody ArticleRequest articleRequest) {
        Article article = new Article(articleRequest.getTitle(), articleRequest.getDescription(),
                articleRequest.getCreatedAt(), articleRequest.getUpdatedAt());
        return articleServiceImpl.createArticle(article);
    }

    @PutMapping("{id}")
    public Article updateArticle(@RequestBody ArticleRequest articleRequest, @PathVariable("id") Long id) {
        Article article = new Article(articleRequest.getTitle(), articleRequest.getDescription(), articleRequest.getUpdatedAt());
        return articleServiceImpl.updateArticle(article, id);
    }

    @DeleteMapping("{id}")
    public Article deleteArticle(@PathVariable("id") Long id) {
        Article article = new Article(LocalDateTime.now());
        return articleServiceImpl.deleteArticle(article, id);
    }
}
