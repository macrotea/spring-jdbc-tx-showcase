package cn.macrotea.showcase.service;

import cn.macrotea.showcase.dao.ArticleDao;
import cn.macrotea.showcase.dao.ArticleTagDao;
import cn.macrotea.showcase.exception.NoPowerException;
import cn.macrotea.showcase.model.Article;
import cn.macrotea.showcase.model.ArticleTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author macrotea@qq.com
 * @since 2014-8-5 下午7:57
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    //主要介绍spring-jdbc事务的整合和测试

    //这里有一个业务方法: deletePair,删除两个对象记录
    //两个delete方法执行后,我们模拟抛出 运行时异常: NoPowerException
    //由于方法标注了:　@Transactional ,故而当抛出异常,则两个dao的操作将会回滚,从而不删除2条数据
    //现在先看下NoPowerException类,好的,观察完毕
    //下面我们到测试用例中测试下这个方法: deletePair

    @Transactional
    public void deletePair(Article a, ArticleTag b, boolean throwException) {

        articleDao.delete(a);

        articleTagDao.delete(b);

        if (throwException) NoPowerException.throwMe();
    }

    //好 到这里

    //只读事务,只能进行数据的查询操作

    //现在进入测试
    @Transactional(readOnly = true)
    public void mustReadOnly(Article a, ArticleTag b, boolean throwException) {

        articleDao.delete(a);

        articleTagDao.delete(b);

        if (throwException) NoPowerException.throwMe();//false则不执行
    }


    //这里不使用声明式事务: @Transaction , 手工实践编程式事务的处理
    //好的 现在看一下测试

    public void addArticleAndTagButMustRollback(final Article a, final ArticleTag ta) {

        //通过 事务模板 来操作

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            //在回调中处理
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                //do add
                articleDao.add(a);
                articleTagDao.add(ta);

                //强制回滚
                status.setRollbackOnly();


                //一般使用模式:

                /*
                try {
                    //xxx
                } catch (Exception e) {
                    //log
                } finally {
                    //回滚事务
                    status.setRollbackOnly();
                }
                */
            }
        });
    }

    //我们看下这个方法,它是内部出现异常,而不是 status.setRollbackOnly();
    public void addArticleAndTagButThrowException(final Article a, final ArticleTag ta, final boolean hasException) {

        //回调中执行
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //do add
                articleDao.add(a);

                articleTagDao.add(ta);

                //模拟抛出异常
                //if throw RuntimeException then rollback auto
                if (hasException) NoPowerException.throwMe();//true
            }
        });

    }

}


