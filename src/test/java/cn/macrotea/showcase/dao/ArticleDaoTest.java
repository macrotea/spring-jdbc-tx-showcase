package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.Article;
import cn.macrotea.showcase.test.AbstractTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

//TODO macrotea@qq.com 2014-08-05 15:55:49 重命名
/**
 * @author macrotea@qq.com
 * @since 2014-8-4 下午9:01
 */
public class ArticleDaoTest extends AbstractTests {

    @Autowired
    private ArticleDao articleDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        Article a = Article.randomMe();
        articleDao.add(a);
        Article target = articleDao.findById(a.getId());

        assertEquals(a.getArticleName(), target.getArticleName());
    }
}
