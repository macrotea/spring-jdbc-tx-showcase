package cn.macrotea.showcase.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:28
 */
@Entity
@Table(name = "tb_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    private Integer age;

    private Double money;

    private Date addTime;

    public static User randomMe() {
        User u = new User();
        u.setUsername(RandomStringUtils.randomAlphanumeric(10));
        u.setPassword(RandomStringUtils.randomAlphanumeric(10));
        u.setAge(Integer.parseInt(RandomStringUtils.randomNumeric(3)));
        u.setMoney(100.0);
        u.setEmail(RandomStringUtils.randomAlphanumeric(6) + "qq.com");
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
