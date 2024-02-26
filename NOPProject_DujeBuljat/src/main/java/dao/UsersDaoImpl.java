package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Users;
import org.hibernate.Session;
import test_server_db.HibernateUtil;

import java.util.List;

public class UsersDaoImpl implements UsersDao{
    @Override
    public void save(Users users) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.persist(users);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Users> getAll() {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Users> query = criteriaBuilder.createQuery(Users.class);
        Root<Users> rootEntry = query.from(Users.class);
        CriteriaQuery<Users> all = query.select(rootEntry);

        TypedQuery<Users> allQuery = session.createQuery(all);

        List<Users> users = allQuery.getResultList();

        session.getTransaction().commit();

        return users;
    }

    @Override
    public Users getById(int userID) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Users user = session.get(Users.class, userID);

        session.getTransaction().commit();

        return user;
    }

    @Override
    public void update(Users users, int userID) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Users existingUser = session.get(Users.class, userID);
        existingUser.setUserName(users.getUserName());
        existingUser.setPassword(users.getPassword());

        session.merge(existingUser);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Users users) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.remove(users);

        session.getTransaction().commit();
    }
}
