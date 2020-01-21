package database;

public interface UserDao {
    String saveUser(String username, String password);
    boolean checkUserCredentials(String username, String password);
    boolean usernameUsed(String username);
    void close();
}
