package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.ArticleAggregate;
import com.cricketfoursix.cricketdomain.aggregate.UserAggregate;
import com.cricketfoursix.cricketdomain.common.article.ArticleResponse;
import com.cricketfoursix.cricketdomain.common.article.ArticleStatus;
import com.cricketfoursix.cricketdomain.common.article.CricketArticle;
import com.cricketfoursix.cricketdomain.common.article.CricketEnthusiast;
import com.cricketfoursix.cricketdomain.repository.ArticleRepository;
import com.cricketfoursix.cricketdomain.repository.UserRepository;
import com.sport.cricket.cricketapi.domain.response.article.ClapArticle;
import com.sport.cricket.cricketapi.domain.response.article.CommentArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            cricketArticle.setPostPara(articleAggregate.getPostPara());
            cricketArticle.setAuthor(articleAggregate.getAuthor());
            cricketArticle.setAuthorInfo(getAuthorDetails(articleAggregate.getAuthor()));
            cricketArticle.setPublished(articleAggregate.getPublishDate());
            cricketArticle.setLastModified(articleAggregate.getLastModified());
            cricketArticle.setClaps(articleAggregate.getClaps());
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
            cricketArticle.setPostPara(articleAggregate.getPostPara());
            cricketArticle.setAuthor(articleAggregate.getAuthor());
            cricketArticle.setLastModified(articleAggregate.getLastModified());
            cricketArticle.setPublished(articleAggregate.getPublishDate());
            cricketArticle.setAuthorInfo(getAuthorDetails(articleAggregate.getAuthor()));
            cricketArticle.setTags(articleAggregate.getTags());

            cricketArticle.setClaps(articleAggregate.getClaps());
            cricketArticle.setArticleResponses(articleAggregate.getArticleResponses());
        }

        return cricketArticle;


    }

    private CricketEnthusiast getAuthorDetails(String userName) {
        if(null != userName){

            Optional<UserAggregate> userAggregateOpt = userRepository.findById(userName);
            if(userAggregateOpt.isPresent()) {
                UserAggregate userAggregate = userAggregateOpt.get();
                CricketEnthusiast cricketEnthusiast = new CricketEnthusiast();
                cricketEnthusiast.setUserName(userAggregate.getUserName());
                cricketEnthusiast.setFirstName(userAggregate.getFirstName());
                cricketEnthusiast.setLastName(userAggregate.getLastName());
                cricketEnthusiast.setEmail(userAggregate.getEmail());
                return cricketEnthusiast;
            }
        }
        return null;
    }


    public CricketArticle updateArticle(CricketArticle article) {

        Optional<ArticleAggregate> articleAggregateOpt = articleRepository.findById(article.getId());
        if(articleAggregateOpt.isPresent()) {
            ArticleAggregate articleAggregate = articleAggregateOpt.get();
            articleAggregate.setTitle(article.getTitle());
            articleAggregate.setPrePara(article.getPrePara());
            articleAggregate.setPostPara(article.getPostPara());
            articleAggregate.setAuthor(article.getAuthor());
            articleAggregate.getTags().add(article.getLeagueTag());
            articleAggregate.getTags().add(article.getTeamTag());
            articleAggregate.getTags().add(article.getPlayerTag());
            articleRepository.save(articleAggregate);
        }

        return article;
    }


    public void clapArticle(ClapArticle clapArticle) {

        Optional<ArticleAggregate> articleAggregateOpt = articleRepository.findById(clapArticle.getArticleId());
        if(articleAggregateOpt.isPresent()) {
            ArticleAggregate articleAggregate = articleAggregateOpt.get();
            articleAggregate.getClaps().add(clapArticle.getUserName());
            articleRepository.save(articleAggregate);
        }
    }

    public void clapArticleComment(ClapArticle clapComment) {

        Optional<ArticleAggregate> articleAggregateOpt = articleRepository.findById(clapComment.getArticleId());
        if(articleAggregateOpt.isPresent()) {

            ArticleAggregate articleAggregate = articleAggregateOpt.get();

            if(null != articleAggregate.getArticleResponses()){

               Optional<ArticleResponse> comment =  articleAggregate.getArticleResponses()
                        .stream()
                        .filter(articleResponse -> articleResponse.getId() == clapComment.getCommentId())
                        .findFirst();
               if(comment.isPresent()){
                   comment.get().getLikes().add(clapComment.getUserName());
               }
            }
            articleRepository.save(articleAggregate);
        }
    }

    public CricketArticle postArticle(CricketArticle article) {

        ArticleAggregate articleAggregate = new ArticleAggregate();
        articleAggregate.setId(new Date().getTime());
        articleAggregate.setTitle(article.getTitle());
        articleAggregate.setPrePara(article.getPrePara());
        articleAggregate.setPostPara(article.getPostPara());
        articleAggregate.setAuthor(article.getAuthor());
        articleAggregate.setLastModified(new Date());
        articleAggregate.setArticleStatus(ArticleStatus.created);
        System.out.println("articleAggregate:"+articleAggregate);
        articleRepository.save(articleAggregate);
        article.setId(articleAggregate.getId());

        return article;
    }


    public CricketArticle postArticleComment(CommentArticle commentArticle) {
        Optional<ArticleAggregate> articleAggregateOpt = articleRepository.findById(commentArticle.getArticleId());
        if(articleAggregateOpt.isPresent()) {

            ArticleAggregate articleAggregate = articleAggregateOpt.get();

            if (null != articleAggregate.getArticleResponses()) {

                ArticleResponse articleResponse = new ArticleResponse();
                articleResponse.setDate(new Date());
                articleResponse.setId(articleResponse.getDate().getTime());
                articleResponse.setCommenter(getAuthorDetails(articleAggregate.getAuthor()));
                articleResponse.setResponse(commentArticle.getComment());
                articleAggregate.getArticleResponses().add(articleResponse);
            }
            articleRepository.save(articleAggregate);
            return fetchArticle(commentArticle.getArticleId());
        }
        return null;
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
