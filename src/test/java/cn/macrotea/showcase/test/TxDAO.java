package cn.macrotea.showcase.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author macrotea@qq.com
 * @since 2014-8-7 上午10:39
 */
public class TxDAO {

    private List<Dao> daoList;

    public TxDAO(Dao... daos) {
        this.daoList = Arrays.asList(daos);
    }

    /**
     * 链式构造
     * @param daos
     * @return
     */
    public static TxDAO from(Dao... daos){
        return new TxDAO(daos);
    }

    /**
     * 链式添加
     * @param dao
     * @return
     */
    public TxDAO add(Dao dao) {
        if(CollectionUtils.isEmpty(this.daoList)) this.daoList = Collections.emptyList();

        this.daoList.add(dao);

        return this;
    }

    /**
     * 执行回调
     * @param action
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T execute(TransactionCallback<T> action) throws RuntimeException {
        T result;

        try {

            //执行回调
            result = action.doInTransaction();

        } catch (RuntimeException ex) {
            //日志
            //log.error(xxxx);

            //回滚
            this.rollback();

            //外抛
            throw ex;
        }

        //最后提交
        this.commit();

        return result;
    }

    private void rollback() {
        // TODO macrotea@qq.com 2014-08-07 11:05:42
    }

    public void commit() {
        // TODO macrotea@qq.com 2014-08-07 11:05:45
    }
}

/**
 * 回调接口
 *
 * @param <T>
 */
interface TransactionCallback<T> {
    T doInTransaction();
}

//-------------------------------------------------------------- Dao

/**
 * Dao类
 */
class Dao {

    public boolean add(Object u1) {
        return true;
    }
}

/**
 * User Dao类
 */
class UserDao extends Dao {

}
/**
 * SecuredUser Dao类
 */
class SecuredUserDao extends Dao {

}

//-------------------------------------------------------------- Model

/**
 * 用户
 */
class User{
    private Long id ;private String name;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

/**
 * 保密用户
 */
class SecuredUser extends User{
    private int age;

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

//-------------------------------------------------------------- Test

/**
 * 测试
 */
class Test {

    public static void main(String[] args) {
        final UserDao userDao = new UserDao();
        final SecuredUserDao securedUserDao = new SecuredUserDao();

        // NOTICE macrotea@qq.com 2014-08-07 11:06:37
        // 这样的代码轮廓比较好一点,dao链式添加,在回调中用户仅仅需要关注他的业务操作,而回滚和commit等操作已经在模板方法(execute)中处理了

        Boolean flag = TxDAO.from(userDao).add(securedUserDao).execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction() {

                //prepare
                User u1 = new User();
                u1.setId(1L);
                u1.setName("macrotea");

                SecuredUser su1 = new SecuredUser();
                su1.setId(2L);
                su1.setName("macrotea2");
                su1.setAge(2);

                //add
                return userDao.add(u1) && securedUserDao.add(su1);
            }
        });

        System.out.println("插入两个用户的操作状态:" + flag);

    }
}