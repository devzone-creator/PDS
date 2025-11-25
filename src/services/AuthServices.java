package services;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class AuthServices {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    public User registerUser(String name, String email, String password){
        User newUser = new User(nextId++, name, email, password);
        newUser.setIsActive(true);

        users.add(newUser);
        System.out.println("User registered: " + name);

        return newUser;
    }

    public User login(String email, String password){
        for(User user : users){
            if(user.getEmail().equals(email)){
                if (user.getPassword().equals(password)){
                    if (user.isActive()){
                        System.out.println("Login successful! " + user.getName());
                        return user;
                    }
                    else {
                        System.out.println("Accounts is inactive");
                        return null;
                    }
                }
                else {
                    System.out.println("Invalid password");
                    return null;
                }
            }
        }
        System.out.println("User not found");
        return null;
    }
}