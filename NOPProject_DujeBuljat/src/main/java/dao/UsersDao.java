package dao;

import model.Users;

import java.util.List;

public interface UsersDao {

    void save(Users users);

    List<Users> getAll();

    Users getById(int userID);

    void update(Users users, int userID);

    void delete(Users users);
}
