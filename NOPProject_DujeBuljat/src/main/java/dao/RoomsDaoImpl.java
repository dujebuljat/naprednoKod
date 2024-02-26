package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Rooms;
import model.Users;
import org.hibernate.Session;
import test_server_db.HibernateUtil;

import java.util.List;

public class RoomsDaoImpl implements RoomsDao{
    @Override
    public void save(Rooms rooms) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.persist(rooms);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Rooms> getAll() {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Rooms> query = criteriaBuilder.createQuery(Rooms.class);
        Root<Rooms> rootEntry = query.from(Rooms.class);
        CriteriaQuery<Rooms> all = query.select(rootEntry);

        TypedQuery<Rooms> allQuery = session.createQuery(all);

        List<Rooms> rooms = allQuery.getResultList();

        session.getTransaction().commit();

        return rooms;
    }

    @Override
    public Rooms getById(int roomID) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Rooms room = session.get(Rooms.class, roomID);

        session.getTransaction().commit();

        return room;
    }

    @Override
    public void update(Rooms rooms, int roomID) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Rooms existingRoom = session.get(Rooms.class, roomID);
        existingRoom.setPrice(rooms.getPrice());

        session.merge(existingRoom);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Rooms rooms) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.remove(rooms);

        session.getTransaction().commit();
    }
}
