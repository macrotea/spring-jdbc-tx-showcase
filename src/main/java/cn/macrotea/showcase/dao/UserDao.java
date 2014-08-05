package cn.macrotea.showcase.dao;

import cn.macrotea.showcase.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author macrotea@qq.com
 * @since 2014-7-28 上午8:33
 */
@Profile("hibernate4")
@Repository
@Transactional(readOnly = true)
@SuppressWarnings({"unchecked"})
public class UserDao {

    private Class<User> entityClazz = User.class;

    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User findById(Long id) {
        return (User) getCurrentSession().get(entityClazz, id);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(User e) {
        getCurrentSession().saveOrUpdate(e);
    }

    @Transactional(readOnly = false)
    public void delete(User e) {
        getCurrentSession().delete(e);
    }

    public List<User> findAll() {
        Query query = getCurrentSession().createQuery("FROM " + entityClazz.getSimpleName());
        query.setCacheable(true);
        List list = query.list();
        return list;
    }
}
