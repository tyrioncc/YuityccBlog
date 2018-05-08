package dev.blog.service.impl;

import dev.blog.service.ArticleService;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.example.db.mysql.tables.Article;
import org.jooq.example.db.mysql.tables.records.ArticleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.jooq.example.db.mysql.tables.Article.ARTICLE;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final DSLContext create;

    @Autowired
    public ArticleServiceImpl(DSLContext create){
        this.create = create;
    }

    public String getArticleTitleById(int id){
        Record1<String> result = create.select(ARTICLE.TITLE).from(ARTICLE).where(ARTICLE.ID.eq(id)).fetchOne();
        return result.value1();
    }

    @Override
    public int getArticleCount() {
        return 0;
    }

    @Override
    public void saveArticle(Article article) {
        create.insertInto(ARTICLE).values(article).execute();
    }

    @Override
    public void updateStatue(int id, int status) {
        create.update(ARTICLE).set(ARTICLE.STATUS, status).where(ARTICLE.ID.eq(id)).execute();
    }

    @Override
    public void addArticleCount(int id) {
        create.update(ARTICLE).set(ARTICLE.SHOW_COUNT, ARTICLE.SHOW_COUNT.add(1))
                .where(ARTICLE.ID.eq(id)).execute();
    }

    @Override
    public List<ArticleRecord> getArticleList(int offset, int limit) {
        return create.selectFrom(ARTICLE).orderBy(ARTICLE.CREATE_TIME.desc()).limit(limit).offset(offset).fetch();
    }


}
