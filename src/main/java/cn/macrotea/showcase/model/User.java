package cn.macrotea.showcase.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:28
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private Integer age;

    private Date addTime;

    public static User randomMe() {
        User u = new User();
        u.setUsername(RandomStringUtils.randomAlphanumeric(10));
        u.setAge(Integer.parseInt(RandomStringUtils.randomNumeric(3)));
        u.setAddTime(new Date());
        return u;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
