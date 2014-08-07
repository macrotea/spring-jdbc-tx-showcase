package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.Article;
import cn.macrotea.showcase.test.AbstractTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//TODO macrotea@qq.com 2014-08-05 15:55:49 重命名

/**
 * @author macrotea@qq.com
 * @since 2014-8-4 下午9:01
 */
public class ArticleDaoTransactionTest extends AbstractTests {

    @Test
    public void test_deletePair() throws Exception {
        //prepare
        Article a = Article.randomMe();
        Article b = Article.randomMe();
        articleDao.add(a);
        articleDao.add(b);

        //check
        assertTrue(articleDao.exist(a.getId()));
        assertTrue(articleDao.exist(b.getId()));
    }

    @Test
    public void test_deletePair_but_no_exception() throws Exception {
        //prepare
        Article a = Article.randomMe();
        Article b = Article.randomMe();
        articleDao.add(a);
        articleDao.add(b);

        //then
        articleDao.deletePair(a, b, false);

        //check
        assertFalse(articleDao.exist(a.getId()));
        assertFalse(articleDao.exist(b.getId()));
    }

    @Test
    public void test_deletePair_but_exception_and_rollback() throws Exception {
        //prepare
        Article a = Article.randomMe();
        Article b = Article.randomMe();
        articleDao.add(a);
        articleDao.add(b);

        //then
        try {
            articleDao.deletePair(a, b, true);
        } catch (Exception ignore) {
            //e.printStackTrace();
        }

        //check
        assertTrue(articleDao.exist(a.getId()));
        assertTrue(articleDao.exist(b.getId()));

        //说明:
        // 若deletePair出现异常则操作都回滚,若回滚则删除失败,故而还余留开始时插入的两条数据
        // 若deletePair方法删除 @Transaction 注解,则成功删除一条记录
        // 若deletePair方法如下注解: @Transaction(readonly=true),则抛出异常:SQLException: Connection is read-only. Queries leading to data modification are not allowed

    }

    @Test
    public void test_dao_proxy_origin() throws Exception {
        //当项目中存在如下依赖: spring-aop 和 aspectj , 但是无cglib , 则如下测试:
        // AOP 代理则可分为静态代理和动态代理两大类，其中静态代理是指使用 AOP 框架提供的命令进行编译，从而在编译阶段就可生成 AOP 代理类，因此也称为编译时增强
        // 而动态代理则在运行时借助于 JDK 动态代理、CGLIB 等在内存中“临时”生成 AOP 动态代理类，因此也被称为运行时增强
        assertTrue(AopUtils.isAopProxy(articleDao));
        assertTrue(AopUtils.isCglibProxy(articleDao));
        assertFalse(AopUtils.isJdkDynamicProxy(articleDao));
    }


    @Test
    public void test_dao_get_manual() throws Exception {
        //prepare
        ApplicationContext ac = new ClassPathXmlApplicationContext("app-root.xml");

        //FIXME macrotea@qq.com/2014-8-5 下午7:01 但是项目中没有cglib的依赖喔,奇怪
        //EnhancerByCGLIB : cn.macrotea.showcase.dao.ArticleDao$$EnhancerByCGLIB$$112a0c@1716
        ArticleDao dao = (ArticleDao) ac.getBean("articleDao");

        //then
        Article a = Article.randomMe();
        dao.add(a);

        //check
        assertTrue(dao.exist(a.getId()));
    }

    @Autowired
    private ArticleDao articleDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

}
