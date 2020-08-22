package com.sport.cricket.cricketapi.controller;

import com.cricketfoursix.cricketdomain.common.article.CricketArticle;
import com.sport.cricket.cricketapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public List<CricketArticle> articles() {
        return articleService.fetchArticles();
    }

    @GetMapping("/articles/{articleId}")
    public CricketArticle article(@PathVariable(value="articleId") Long articleId) {
        return articleService.fetchArticle(articleId);
    }

    @DeleteMapping("/articles/{articleId}")
    public void deleteArticle(@PathVariable(value="articleId") Long articleId) {
         articleService.deleteArticle(articleId);
    }


    @PostMapping("/articles")
    public CricketArticle updateArticle(@RequestBody CricketArticle article){
        return articleService.updateArticle(article);
    }

    @PutMapping("/articles")
    public CricketArticle createArticle(@RequestBody CricketArticle article){
        return articleService.postArticle(article);
    }
}
