package database;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabaseDao implements UserDao {

    DatabaseConnection databaseConnection;

    public UserDatabaseDao() throws Exception {
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.createStatement();
        } catch (SQLException e) {
          throw new Exception("connection to database failed");
        }
    }

    @Override
    public String saveUser(String username, String password) {
        try {
            String salt = PasswordUtils.genSalt();
            StringBuilder builder = new StringBuilder();
            builder.append("insert into User values(null,'");
            builder.append(username);
            builder.append("','");
            builder.append(PasswordUtils.saltPassword(password, salt));
            builder.append("','");
            builder.append(salt);
            builder.append("');");
            String query = builder.toString();
            databaseConnection.execute(query);
        }
        catch (SQLException e ){
            if(e.getErrorCode() == 1062) {
                return "username is in use";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "ok";
    }

    @Override
    public boolean checkUserCredentials(String username, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append("select password, salt from user where username='");
        builder.append(username);
        builder.append("';");
        String query = builder.toString();
        try {
            ResultSet resultSet = databaseConnection.executeQuery(query);
            if(resultSet.first()) {
                String hashed = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                return PasswordUtils.checkPassword(hashed, password, salt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean usernameUsed(String username) {
        StringBuilder builder = new StringBuilder();
        builder.append("select username from user where username='");
        builder.append(username);
        builder.append("';");
        String query = builder.toString();
        try {
            ResultSet resultSet = databaseConnection.executeQuery(query);
            if(!resultSet.first()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void close() {
        try {
            databaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
