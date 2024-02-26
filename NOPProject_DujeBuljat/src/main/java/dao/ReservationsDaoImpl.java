package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Reservations;
import org.hibernate.Session;
import test_server_db.HibernateUtil;

import java.util.List;

public class ReservationsDaoImpl implements ReservationsDao{
    @Override
    public void save(Reservations reservations) {
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

//            Reservations newReservation = new Reservations();
//            newReservation.setRoomID(reservations.getRoomID());
//            newReservation.setUserID(reservations.getUserID());
//            newReservation.setfName(reservations.getfName());
//            newReservation.setlName(reservations.getlName());
//            newReservation.setPhoneNumber(reservations.getPhoneNumber());
//            newReservation.setEmail(reservations.getEmail());
//            newReservation.setDateIn(reservations.getDateIn());
//            newReservation.setDateOut(reservations.getDateOut());
//            newReservation.setRoomType(reservations.getRoomType());
//            newReservation.setPaymentMethod(reservations.getPaymentMethod());
//            newReservation.setTotalPrice(reservations.getTotalPrice());
//
//            session.save(newReservation);

            session.merge(reservations); // potencijalno, testirat
//            session.persist(reservations);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reservations> getAll() {
        List<Reservations> reservations;
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Reservations> query = criteriaBuilder.createQuery(Reservations.class);
            Root<Reservations> rootEntry = query.from(Reservations.class);
            CriteriaQuery<Reservations> all = query.select(rootEntry);

            TypedQuery<Reservations> allQuery = session.createQuery(all);

            reservations = allQuery.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return reservations;
    }

    @Override
    public Reservations getById(int reservationID) {
        Reservations reservations;
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            reservations = session.get(Reservations.class, reservationID);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return reservations;
    }

    @Override
    public void update(Reservations reservations, int reservationID) {
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            Reservations existingReservation = session.get(Reservations.class, reservationID);
            existingReservation.setRoomID(reservations.getRoomID());
            existingReservation.setUserID(reservations.getUserID());
            existingReservation.setfName(reservations.getfName());
            existingReservation.setlName(reservations.getlName());
            existingReservation.setPhoneNumber(reservations.getPhoneNumber());
            existingReservation.setEmail(reservations.getEmail());
            existingReservation.setDateIn(reservations.getDateIn());
            existingReservation.setDateOut(reservations.getDateOut());
            existingReservation.setRoomType(reservations.getRoomType());
            existingReservation.setPaymentMethod(reservations.getPaymentMethod());
            existingReservation.setTotalPrice(reservations.getTotalPrice());

            session.merge(existingReservation);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reservations reservations) {
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            session.remove(reservations);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
