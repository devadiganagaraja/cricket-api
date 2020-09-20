package com.sport.cricket.cricketapi.controller;

import com.cricketfoursix.cricketdomain.common.article.CricketArticle;
import com.sport.cricket.cricketapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public List<CricketArticle> articles() {
        return articleService.fetchArticles();
    }

    @GetMapping("/articles/{articleId}")
    public CricketArticle article(@PathVariable(value="articleId") Long articleId) {
        int String = 0;

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


    @PostMapping("/articles/clap")
    public void clapArticle(@RequestBody ClapArticle clapArticle){
         articleService.clapArticle(clapArticle);
    }

    @PostMapping("/articles/clap-comment")
    public void clapArticleComment(@RequestBody ClapArticle clapComment){
        articleService.clapArticleComment(clapComment);
    }

    @PutMapping("/articles")
    public CricketArticle createArticle(@RequestBody CricketArticle article){

        System.out.println("put article:"+article);
        return articleService.postArticle(article);
    }


    @PutMapping("/articles/comment")
    public CricketArticle createArticleComment(@RequestBody CommentArticle commentArticle){

        System.out.println("put article comment:"+commentArticle);
        return articleService.postArticleComment(commentArticle);
    }
}
