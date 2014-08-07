package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.ArticleTag;
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

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:33
 */
@Repository
public class ArticleTagDao {
    private static final Class<ArticleTag> MODEL_CLAZZ = ArticleTag.class;
    private static final String INSERT_SQL = "insert into tb_article_tag(tagName,addTime) values(?,?)";
    private static final String DELETE_SQL = "delete from tb_article_tag where id = ?";
    private static final String UPDATE_SQL = "update tb_article_tag set tagName = ?,addTime = ? where id = ?";
    private static final String FIND_ALL_SQL = "select * from tb_article_tag";
    private static final String FIND_ONE_SQL = "select * from tb_article_tag where id = ?";
    private static final String ID_COLUMN = "id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(final ArticleTag tag) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                final PreparedStatement ps = conn.prepareStatement(INSERT_SQL, new String[]{ID_COLUMN});
                ps.setString(1, tag.getTagName());
                ps.setTimestamp(2, new java.sql.Timestamp(tag.getAddTime().getTime()));
                return ps;
            }
        }, keyHolder);

        tag.setId(keyHolder.getKey().longValue());
    }

    @Transactional
    public int update(ArticleTag tag) {
        Assert.notNull(tag.getId(), "在进行更新操作时,Id不能为NULL");
        return jdbcTemplate.update(UPDATE_SQL, tag.getTagName(), tag.getAddTime(), tag.getId());
    }

    @Transactional
    public int delete(ArticleTag tag) {
        Assert.notNull(tag.getId(), "在进行更新删除时,Id不能为NULL");
        return jdbcTemplate.update(DELETE_SQL, tag.getId());
    }

    @Transactional(readOnly = true)
    public List<ArticleTag> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, ParameterizedBeanPropertyRowMapper.newInstance(MODEL_CLAZZ));
    }

    @Transactional(readOnly = true)
    public ArticleTag findById(Long id) {
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

