package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.exception.NoPowerException;
import cn.macrotea.showcase.model.Article;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

// TODO macrotea@qq.com 2014-08-05 16:09:16 文字有问题: 在进行更新操作时

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:33
 */
@Repository
public class ArticleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*========================================================
     * 事务测试
     *========================================================*/
    @Transactional
    public void deletePair(Article a, Article b, boolean throwException) {


        jdbcTemplate.update(DELETE_SQL, a.getId());

        if (throwException) NoPowerException.throwMe();

        jdbcTemplate.update(DELETE_SQL, b.getId());
    }

    /*========================================================
     * 基础方法
     *========================================================*/

    private static final Class<Article> MODEL_CLAZZ = Article.class;
    private static final String INSERT_SQL = "insert into tb_article(articleName,addTime) values(?,?)";
    private static final String DELETE_SQL = "delete from tb_article where id = ?";
    private static final String UPDATE_SQL = "update tb_article set articleName = ?,addTime = ? where id = ?";
    private static final String FIND_ALL_SQL = "select * from tb_article";
    private static final String FIND_ONE_SQL = "select * from tb_article where id = ?";
    private static final String ID_COLUMN = "id";

    @Transactional
    public void add(final Article a) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                final PreparedStatement ps = conn.prepareStatement(INSERT_SQL, new String[]{ID_COLUMN});
                ps.setString(1, a.getArticleName());
                ps.setTimestamp(2, new java.sql.Timestamp(a.getAddTime().getTime()));
                return ps;
            }
        }, keyHolder);

        a.setId(keyHolder.getKey().longValue());
    }

    @Transactional
    public int update(Article a) {
        Assert.notNull(a.getId(), "在进行更新操作时,Id不能为NULL");
        return jdbcTemplate.update(UPDATE_SQL, a.getArticleName(), a.getAddTime(), a.getId());
    }

    @Transactional
    public int delete(Article a) {
        Assert.notNull(a.getId(), "在进行更新删除时,Id不能为NULL");
        return jdbcTemplate.update(DELETE_SQL, a.getId());
    }


    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }

    @Transactional(readOnly = true)
    public Article findById(Long id) {
        Assert.notNull(id, "在进行根据Id查询时,Id不能为NULL");
        try {
            return jdbcTemplate.queryForObject(FIND_ONE_SQL, new Object[]{id}, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public boolean exist(Long id) {
        Assert.notNull(id, "在进行根据Id查询记录是否存在时,Id不能为NULL");
        return findById(id) != null;
    }

}



