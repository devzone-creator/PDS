import model.User;
import services.AuthServices;

public class Main {
    public static void main(String[] args) {
        AuthServices authServices = new AuthServices();

        System.out.println("=== Registering User ===");
        authServices.registerUser("Abu", "abu@gmail.com", "pass123");
        authServices.registerUser("Yakubu", "yakubu@gmail.com", "pass143");
        authServices.registerUser("Karim", "karim@gmail.com", "pass153");

        System.out.println("\n=== Testing Login ===");
        User user1 = authServices.login("abu@gmail.com", "pass123");
        User user2 = authServices.login("yakubu@gmail.com", "pass143");
        User user3 = authServices.login("karim@gmail.com", "pass153");

        System.out.println("\n=== Testing Failed login ===");
        User failedLogin = authServices.login("abu@gmail.com", "wrongpass");

        System.out.println("\n=== Testing user logout ===");
        authServices.logout();

        User user = authServices.login("karim@gmail.com", "pass153");
        authServices.logout();

        System.out.println("\n=== Hashed password ===");
        System.out.println("Original pass123");
        System.out.println("Hashed : " + authServices.hashPassword("pass123"));

        authServices.changeUserRole("abu@gmail.com", "admin");
        User admin = authServices.login("abu@gmail.com", "pass123");

        System.out.println("Can admin delete ? " + authServices.checkPermission(admin, "delete"));
        System.out.println("Can admin view ? " + authServices.checkPermission(admin, "view"));
    }
}