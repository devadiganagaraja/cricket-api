package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.ArticleAggregate;
import com.cricketfoursix.cricketdomain.aggregate.UserAggregate;
import com.cricketfoursix.cricketdomain.common.article.ArticleAuthor;
import com.cricketfoursix.cricketdomain.common.article.CricketArticle;
import com.cricketfoursix.cricketdomain.repository.ArticleRepository;
import com.cricketfoursix.cricketdomain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;
    public List<CricketArticle> fetchArticles() {
        List<CricketArticle> cricketArticles = new ArrayList<>();

        List<ArticleAggregate> articles = articleRepository.findAll();

        articles.stream().forEach(articleAggregate -> {
            CricketArticle cricketArticle = new CricketArticle();
            cricketArticle.setId(articleAggregate.getId());
            cricketArticle.setTitle(articleAggregate.getTitle());
            cricketArticle.setPrePara(articleAggregate.getPrePara());
            cricketArticle.setImage(articleAggregate.getImage());
            cricketArticle.setPostPara(articleAggregate.getPostPara());
            cricketArticle.setAuthor(articleAggregate.getAuthor());
            cricketArticles.add(cricketArticle);
        });
        return cricketArticles;
    }

    public CricketArticle fetchArticle(Long articleId) {
        Optional<ArticleAggregate> articleOp = articleRepository.findById(articleId);
        CricketArticle cricketArticle = new CricketArticle();
        if(articleOp.isPresent()){
            ArticleAggregate articleAggregate =  articleOp.get();
            cricketArticle.setId(articleAggregate.getId());
            cricketArticle.setTitle(articleAggregate.getTitle());
            cricketArticle.setPrePara(articleAggregate.getPrePara());
            cricketArticle.setImage(articleAggregate.getImage());
            cricketArticle.setPostPara(articleAggregate.getPostPara());
            cricketArticle.setAuthor(articleAggregate.getAuthor());
            if(null != articleAggregate.getAuthor()){

                Optional<UserAggregate> userAggregateOpt = userRepository.findById(articleAggregate.getAuthor());
                if(userAggregateOpt.isPresent()) {
                    UserAggregate userAggregate = userAggregateOpt.get();
                    ArticleAuthor articleAuthor = new ArticleAuthor();
                    articleAuthor.setUserName(userAggregate.getUserName());
                    articleAuthor.setFirstName(userAggregate.getFirstName());
                    articleAuthor.setLastName(userAggregate.getLastName());
                    articleAuthor.setEmail(userAggregate.getEmail());
                    cricketArticle.setAuthorInfo(articleAuthor);
                }
            }
        }

        return cricketArticle;


    }


    public CricketArticle updateArticle(CricketArticle article) {

        Optional<ArticleAggregate> articleAggregateOpt = articleRepository.findById(article.getId());
        if(articleAggregateOpt.isPresent()) {
            ArticleAggregate articleAggregate = articleAggregateOpt.get();
            articleAggregate.setTitle(article.getTitle());
            articleAggregate.setPrePara(article.getPrePara());
            articleAggregate.setImage(article.getImage());
            articleAggregate.setPostPara(article.getPostPara());
            articleAggregate.setAuthor(article.getAuthor());
            articleRepository.save(articleAggregate);
        }

        return article;
    }

    public CricketArticle postArticle(CricketArticle article) {

        ArticleAggregate articleAggregate = new ArticleAggregate();
        articleAggregate.setId(new Date().getTime());
        articleAggregate.setTitle(article.getTitle());
        articleAggregate.setPrePara(article.getPrePara());
        articleAggregate.setImage(article.getImage());
        articleAggregate.setPostPara(article.getPostPara());
        articleAggregate.setAuthor(article.getAuthor());
        articleRepository.save(articleAggregate);
        articleAggregate.setId(articleAggregate.getId());

        return article;
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
