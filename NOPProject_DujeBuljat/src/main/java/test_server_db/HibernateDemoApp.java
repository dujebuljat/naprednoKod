package test_server_db;

import dao.ReservationsDao;
import dao.ReservationsDaoImpl;

import model.Reservations;

import java.time.LocalDate;

public class HibernateDemoApp {

    public static void main(String[] args) {
        ReservationsDao reservationsDao = new ReservationsDaoImpl();

        // SAVE
        reservationsDao.save(new Reservations(1, 1, 1, "Duje", "Buljat", 123456, "db@gm.com", "2020-12-12", "2020-12-14", "OneBed", "Cash", 100));
        reservationsDao.save(new Reservations(2, 2, 2, "Daria", "Babić", 654321, "db@gm.com", "2020-12-15", "2020-12-18", "TwoBeds", "Google Pay", 300));


        System.out.println("Getting all reservations from the database: ");
        // GET ALL
        System.out.println(reservationsDao.getAll());

        // UPDATE
//        reservationsDao.update(new Reservations("Batman vs Superman", "Zack Snyder", LocalDate.of(2015, 1, 1)), 1L);


        System.out.println("Getting reservation with ID 1 from the database: ");
        // GET BY ID
        System.out.println(reservationsDao.getById(1));

        // DELETE
//        reservationsDao.delete(reservationsDao.getById(1L));


//        reservationsDao.save(new Movie("The Dark Knight: Rises", "Christopher Nolan", LocalDate.of(2012, 7, 16)));
//        // CACHING
//        try {
//            reservationsDao.getByIdCacheExample(2L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}