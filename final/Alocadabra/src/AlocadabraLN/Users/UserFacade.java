package AlocadabraLN.Users;

import AlocadabraDL.UserDAO;

public class UserFacade implements IGestUsers {
    private final UserDAO users;

    public UserFacade() {
        this.users = new UserDAO();
    }

    public User login(String email, String password) {
        return users.authenticate(email, password);
    }

    public User register(String email, String password) {
        return users.register(email, password);
    }
}
