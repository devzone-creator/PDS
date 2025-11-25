import model.User;
import services.AuthServices;

public class Main {
    public static void main(String[] args) {
        AuthServices authServices = new AuthServices();

        System.out.println("=== Registering User ===");
        authServices.registerUser("Abu", "abu@gmail.com", "pass123");

        System.out.println("\n=== Testing Login ===");
        User loggedInUser = authServices.login("abu@gmail.com", "pass123");

        System.out.println("\n=== Testing Failed login ===");
        User failedLogin = authServices.login("abu@gmail.com", "wrongpass");
    }
}