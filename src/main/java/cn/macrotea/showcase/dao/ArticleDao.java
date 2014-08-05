package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.Article;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:33
 */
public class ArticleDao extends JdbcDaoSupport {
    private static final Class<Article> MODEL_CLAZZ = Article.class;
    private static final String ADD_SQL = "insert into tb_article(articleName) values(?)";
    private static final String DELETE_SQL = "delete from tb_article where id = ?";
    private static final String UPDATE_SQL = "update tb_article set articleName = ? where id = ?";
    private static final String FIND_ALL_SQL = "select * from tb_article";
    private static final String FIND_ONE_SQL = "select * from tb_article where id = ?";
    private static final String ID_COLUMN = "id";

    public void add(final Article a) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                final PreparedStatement ps = conn.prepareStatement(ADD_SQL, new String[]{ID_COLUMN});
                ps.setString(1, a.getArticleName());
                return ps;
            }
        }, keyHolder);

        a.setId(keyHolder.getKey().longValue());
    }

    public int update(Article a) {
        Assert.notNull(a.getId(), "在进行更新操作时,Id不能为NULL");
        return getJdbcTemplate().update(UPDATE_SQL, a.getArticleName(), a.getId());
    }

    public int delete(Article a) {
        Assert.notNull(a.getId(), "在进行更新删除时,Id不能为NULL");
        return getJdbcTemplate().update(DELETE_SQL, a.getId());
    }

    public List<Article> findAll() {
        return getJdbcTemplate().query(FIND_ALL_SQL, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }

    public Article findById(Long id) {
        Assert.notNull(id, "在进行根据Id查询时,Id不能为NULL");
        return getJdbcTemplate().queryForObject(FIND_ONE_SQL, new Object[]{id}, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }
}

