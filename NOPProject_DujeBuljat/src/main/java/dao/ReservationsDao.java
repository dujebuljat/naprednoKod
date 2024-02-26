package dao;

import model.Reservations;

import java.util.List;

public interface ReservationsDao {

    void save(Reservations reservations);

    List<Reservations> getAll();

    Reservations getById(int reservationID);

    void update(Reservations reservations, int reservationID);

    void delete(Reservations reservations);
}
