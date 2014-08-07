package cn.macrotea.showcase.exception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author macrotea@qq.com
 * @since 2014-8-7 上午11:36
 */
public class Test {
    private Connection connection;

    //按顺序阅读
    class UserDao{

        public int updateUser1() {
            try {
                String sql = "update user set firstname=? , lastname=? where id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "Gary");
                preparedStatement.setString(2, "Larson");
                preparedStatement.setLong(3, 123);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                //底层jdbc执行可能抛出检查异常: SQLException
                //当出现这个异常的时候,你底层可以log说更新出现了这个异常
                //但是调用方不知道你出现了这个异常
                //调用方只能通过是否返回值大于0来判断是否出现异常
                //为什么呢,因为你底层的exception被捕获拦截了,没有外抛
            }
            return -1;
        }

        public int updateUser2() throws SQLException {
            try {
                String sql = "update user set firstname=? , lastname=? where id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "Gary");
                preparedStatement.setString(2, "Larson");
                preparedStatement.setLong(3, 123);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                //底层jdbc执行可能抛出检查异常: SQLException
                //log

                //现在我们外抛异常,但是方法签名会多了:　throws SQLException
                //这样的话,调用方需要try-catch,异常链扩张了
                throw e;
            }
        }

        public int updateUser3(){
            try {
                String sql = "update user set firstname=? , lastname=? where id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "Gary");
                preparedStatement.setString(2, "Larson");
                preparedStatement.setLong(3, 123);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                //底层jdbc执行可能抛出检查异常: SQLException
                //log

                //现在我们需要调用方可以优雅调用方法,但是不需要try-catch,但是调用方想当出现异常的话,自己做相关处理也可以
                //不过我们要包装一下异常
                //包装后的异常就是非检查异常,也叫运行时异常,当我们抛出的是非检查异常的话
                //那么方法签名不需要声明了
                throw new GodException("上帝啊,更新出错了",e);
            }
        }
    }


    public static void main(String[] args) {

        // JAVA异常设计原则,可以了解下思想 : http://luckaway.iteye.com/blog/857443

        //模拟用户调用的场景
        new Test().mockClientCallUpdateUser3Case1();
        new Test().mockClientCallUpdateUser3Case2();

    }

    private void mockClientCallUpdateUser3Case1() {
        //代码执行正常,用户得到返回结果
        int result = new UserDao().updateUser3();

        //根据结果进行下一步操作
        if (result > 0) {
            ///
        }else{
            ///
        }
    }


    private void mockClientCallUpdateUser3Case2() {
        //代码调用可能出现异常,我不用理,当出现异常,页面去到错误页
        int result = new UserDao().updateUser3();

        //我按照当执行正常的时候,接下来的代码该怎么写就怎么写,反正出现异常会不断被抛出,知道servlet容器检测到异常,然后帮我跳转到错误页
        //我还可以根据不同的异常去到不同的页面
    }

    private void mockClientCallUpdateUser3Case3() {
        //代码调用可能出现异常喔,因此我需要特别照顾一下异常,从而做其他的更多操作
        try {
            new UserDao().updateUser3();
        } catch (RuntimeException e) {
            //底层的异常都被包装成RuntimeException的子类
            //其他操作,例如事务处理
            //我写的TxDao就是需要根据来自底层的抛出的异常从而进行事务处理
        }
    }


}
