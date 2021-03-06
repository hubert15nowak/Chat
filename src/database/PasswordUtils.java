package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtils {

    static private Random random = new Random();
    static private final int SALT_SIZE = 10;

    public static String genSalt() {
        byte[] bytes = new byte[SALT_SIZE];
        random.nextBytes(bytes);
        String salt = new String(bytes, StandardCharsets.UTF_8);
        return salt;
    }

    public static String saltPassword(String password, String salt) throws NoSuchAlgorithmException {
        String ps = password+salt;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new String(digest.digest(ps.getBytes()));
    }

    public static boolean checkPassword(String hashed, String password, String salt) throws NoSuchAlgorithmException {
        return hashed.equals(saltPassword(password,salt));
    }
}
