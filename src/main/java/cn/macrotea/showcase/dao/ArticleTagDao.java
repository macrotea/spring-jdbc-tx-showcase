package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.ArticleTag;
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
public class ArticleTagDao extends JdbcDaoSupport {
    private static final Class<ArticleTag> MODEL_CLAZZ = ArticleTag.class;
    private static final String ADD_SQL = "insert into tb_article_tag(tagName) values(?)";
    private static final String DELETE_SQL = "delete from tb_article_tag where id = ?";
    private static final String UPDATE_SQL = "update tb_article_tag set tagName = ? where id = ?";
    private static final String FIND_ALL_SQL = "select * from tb_article_tag";
    private static final String FIND_ONE_SQL = "select * from tb_article_tag where id = ?";
    private static final String ID_COLUMN = "id";

    public void add(final ArticleTag tag) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                final PreparedStatement ps = conn.prepareStatement(ADD_SQL, new String[]{ID_COLUMN});
                ps.setString(1, tag.getTagName());
                return ps;
            }
        }, keyHolder);

        tag.setId(keyHolder.getKey().longValue());
    }

    public int update(ArticleTag tag) {
        Assert.notNull(tag.getId(), "在进行更新操作时,Id不能为NULL");
        return getJdbcTemplate().update(UPDATE_SQL, tag.getTagName(), tag.getId());
    }

    public int delete(ArticleTag tag) {
        Assert.notNull(tag.getId(), "在进行更新删除时,Id不能为NULL");
        return getJdbcTemplate().update(DELETE_SQL, tag.getId());
    }

    public List<ArticleTag> findAll() {
        return getJdbcTemplate().query(FIND_ALL_SQL, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }

    public ArticleTag findById(Long id) {
        Assert.notNull(id, "在进行根据Id查询时,Id不能为NULL");
        return getJdbcTemplate().queryForObject(FIND_ONE_SQL, new Object[]{id}, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }
}

