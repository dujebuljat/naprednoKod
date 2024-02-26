package dao;

import model.Rooms;

import java.util.List;

public interface RoomsDao {

    void save(Rooms rooms);

    List<Rooms> getAll();

    Rooms getById(int roomID);

    void update(Rooms rooms, int roomID);

    void delete(Rooms rooms);
}
