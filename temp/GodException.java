package cn.macrotea.showcase.exception;

/**
 * 非检查异常的子类
 * @author macrotea@qq.com
 * @since 2014-8-7 上午11:47
 */
public class GodException extends RuntimeException {

    public GodException(String msg) {
        super(msg);
    }

    public GodException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
