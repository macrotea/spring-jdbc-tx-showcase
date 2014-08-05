package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.ArticleTag;
import cn.macrotea.showcase.test.AbstractTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;

/**
 * @author macrotea@qq.com
 * @since 2014-8-4 下午9:01
 */
@ActiveProfiles({"mysql", "dbPrepare", "jdbcTemplate"})
public class ArticleTagTest extends AbstractTests {

    @Autowired
    private ArticleTagDao articleTagDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        ArticleTag at = ArticleTag.randomMe();
        articleTagDao.add(at);
        ArticleTag target = articleTagDao.findById(at.getId());

        assertEquals(at.getTagName(), target.getTagName());
    }
}
