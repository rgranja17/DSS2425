package AlocadabraLN.Users;

public interface IGestUsers {
    User login(String email, String password);

    User register(String email, String password);
}
