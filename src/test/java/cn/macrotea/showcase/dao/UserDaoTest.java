package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.User;
import cn.macrotea.showcase.test.AbstractTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * @author macrotea@qq.com
 * @since 2014-8-4 下午9:01
 */
public class UserDaoTest extends AbstractTests {

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        User u = User.randomMe();
        userDao.add(u);
        User target = userDao.findById(u.getId());

        assertEquals(u.getUsername(), target.getUsername());
    }
}
