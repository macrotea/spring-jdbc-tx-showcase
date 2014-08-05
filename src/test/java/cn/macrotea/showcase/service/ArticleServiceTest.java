package cn.macrotea.showcase.service;

import cn.macrotea.showcase.dao.ArticleDao;
import cn.macrotea.showcase.dao.ArticleTagDao;
import cn.macrotea.showcase.exception.NoPowerException;
import cn.macrotea.showcase.model.Article;
import cn.macrotea.showcase.model.ArticleTag;
import cn.macrotea.showcase.test.AbstractTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author macrotea@qq.com
 * @since 2014-8-5 下午9:24
 */
public class ArticleServiceTest extends AbstractTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_addArticleAndTagButMustRollback() throws Exception {
        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        articleService.addArticleAndTagButMustRollback(a, ta);

        //check
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        assertNull(target);
        assertNull(found);
    }

    @Test
    public void test_addArticleAndTagButNoException() throws Exception {
        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        articleService.addArticleAndTagButThrowException(a, ta, false);

        //check
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        assertNotNull(target);
        assertNotNull(found);
    }

    @Test(expected = NoPowerException.class)
    public void test_addArticleAndTagButThrowException() throws Exception {
        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        articleService.addArticleAndTagButThrowException(a, ta, true);

        //check
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        assertNull(target);
        assertNull(found);
    }
}
