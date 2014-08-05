package cn.macrotea.showcase.service;

import cn.macrotea.showcase.dao.ArticleDao;
import cn.macrotea.showcase.dao.ArticleTagDao;
import cn.macrotea.showcase.exception.NoPowerException;
import cn.macrotea.showcase.model.Article;
import cn.macrotea.showcase.model.ArticleTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
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

    //这里不使用声明式事务: @Transaction , 手工实践编程性事务的处理
    public void addArticleAndTagButMustRollback(final Article a, final ArticleTag ta) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
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

    public void addArticleAndTagButThrowException(final Article a, final ArticleTag ta, final boolean exception) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //do add
                articleDao.add(a);

                //if throw RuntimeException then rollback auto
                if(exception) NoPowerException.throwMe();

                articleTagDao.add(ta);
            }
        });

    }

}


