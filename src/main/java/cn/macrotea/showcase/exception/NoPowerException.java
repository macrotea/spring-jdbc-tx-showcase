package cn.macrotea.showcase.exception;

import org.springframework.dao.DataAccessException;

/**
 * 断电异常
 * 此异常目的是为了让Spring检测到RuntimeException,从而回滚事务
 *
 * 我们现在看一下 NoPowerException 类的继承关系,可以看到它是继承RuntimeException的
 *
 * @author macrotea@qq.com
 * @since 2014-8-5 下午4:00
 */
public class NoPowerException extends DataAccessException {

    public NoPowerException(String message) {
        super(message);
    }

    public static void throwMe() {
        throw new NoPowerException("电脑断电运行时异常");
    }
}
