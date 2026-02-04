package dao;

import model.User;

public interface UserDAO {
    User authenticate(String username, String password);

    int createUser(String username, String password, String role);

    User findById(int userId);
}
