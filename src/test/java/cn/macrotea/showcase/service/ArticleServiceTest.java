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
import org.springframework.dao.TransientDataAccessResourceException;

import static org.junit.Assert.*;

//我们最后再集中测试一次
//好,测试都通过了,呵呵
//88 thanks

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

    //测试 deletePair方法的事务是否生效


    @Test
    public void test_deletePair() {

        //事先插入两条数据

        //prepare
        Article a = Article.randomMe();
        articleDao.add(a);
        ArticleTag ta = ArticleTag.randomMe();
        articleTagDao.add(ta);

        //then
        try {
            //true 使得方法内部抛出异常
            articleService.deletePair(a, ta, true);
        } catch (Exception ignore) {
        }

        //若抛出异常,那么刚插入的两条数据将不被删除,故而exist方法返回true,因此 assertTrue()方法 测试通过
        //这个好理解,deletepaire内部出现异常,故而事务回滚,里面进行的delete操作都回滚,若回滚的话
        //你再查询数据库肯定还存在事先插入的记录,故而测试通过

        //check
        assertTrue(articleDao.exist(a.getId()));
        assertTrue(articleTagDao.exist(ta.getId()));
    }

    @Test
    public void test_mustReadOnly() {

        //事先插入两条数据
        //prepare
        Article a = Article.randomMe();
        articleDao.add(a);
        ArticleTag ta = ArticleTag.randomMe();
        articleTagDao.add(ta);

        //then
        try {

            //true 使得方法出现异常,事务回滚

            articleService.mustReadOnly(a, ta, true);
        } catch (Exception ignore) {
        }

        //因为事务回滚,故而刚插入的两条记录尚未被删除,故而还存在,因此测试通过

        //check
        assertTrue(articleDao.exist(a.getId()));
        assertTrue(articleTagDao.exist(ta.getId()));
    }

    //期待出现 TransientDataAccessResourceException 异常
    @Test(expected = TransientDataAccessResourceException.class)
    public void test_mustReadOnly_and_throw_exception() {

        //事先插入两条数据
        //prepare
        Article a = Article.randomMe();
        articleDao.add(a);
        ArticleTag ta = ArticleTag.randomMe();
        articleTagDao.add(ta);

        //then
        //but exception
        //false导致方法内部不出现异常,故而完整执行完,但是提交时发现是写操作,但是由于是只读事务,故而抛出异常
        articleService.mustReadOnly(a, ta, false);
    }

    @Test
    public void test_addArticleAndTagButMustRollback() throws Exception {

        //事先插入两条数据

        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        //方法内部插入两条数据,但是又强制事务回滚了,故而数据尚未提交持久到数据库
        articleService.addArticleAndTagButMustRollback(a, ta);

        //check
        //因此我们再根据id查询,发现记录不存在,故而测试通过
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        //它们都为null
        assertNull(target);
        assertNull(found);
    }

    @Test
    public void test_addArticleAndTagButNoException_no_rollback() throws Exception {

        //事先插入两条数据

        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        // false 导致内部不出现异常,不出现异常则不会回滚
        articleService.addArticleAndTagButThrowException(a, ta, false);

        //check
        //因此我们再根据id查询,记录存在,记录不为空,故而测试通过
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        assertNotNull(target);
        assertNotNull(found);
    }

    //期待出现 NoPowerException 异常
    @Test(expected = NoPowerException.class)
    public void test_addArticleAndTagButThrowException() throws Exception {
        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        articleService.addArticleAndTagButThrowException(a, ta, true);
    }

    @Test
    public void test_addArticleAndTagButThrowException_rollback() throws Exception {
        //prepare
        Article a = Article.randomMe();
        ArticleTag ta = ArticleTag.randomMe();

        //then
        try {
            //true导致出现异常,因此事务回滚
            articleService.addArticleAndTagButThrowException(a, ta, true);
        } catch (Exception e) {
        }

        //check
        //由于事务回滚,因此我们再根据id查询,记录肯定不存在,故而测试通过
        Article target = articleDao.findById(a.getId());
        ArticleTag found = articleTagDao.findById(ta.getId());

        assertNull(target);
        assertNull(found);

    }
}
