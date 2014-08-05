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
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String articleName;

    private Date addTime;

    public static Article randomMe() {
        Article a = new Article();
        a.setArticleName(RandomStringUtils.randomAlphanumeric(10));
        a.setAddTime(new Date());
        return a;
    }

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
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
