package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }

    public User login(String username, String password) {
        User user = userDAO.authenticate(username, password);
        if (user == null) {
            System.out.println("Invalid username or password!");
        }
        return user;
    }
}
