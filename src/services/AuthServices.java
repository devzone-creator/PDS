package services;

import model.User;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class AuthServices {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;
    private User currentUser;
    private final String FILE_NAME = "users.dat";

    public AuthServices(){
        loadUserFromFile();
    }

    private void saveUsersToFile(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
            oos.writeObject(users);
            oos.writeInt(nextId);
            System.out.println("Users saved to file");
        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private void loadUserFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            users = (List<User>) ois.readObject();
            nextId = ois.readInt();
            System.out.println("Users loaded from file");
        } catch (FileNotFoundException e) {
            System.out.println("No saved users found, starting fresh");
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error loading users : " + e.getMessage());
        }
    }

    //Hashing password
    public String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            //converting to hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch(Exception e){
            throw new RuntimeException("Error hashing password");
        }
    }

    public User registerUser(String name, String email, String password){
        String hashedPassword = hashPassword(password);
        User newUser = new User(nextId++, name, email, password, hashedPassword);
        newUser.setRole("user");
        newUser.setIsActive(true);
        users.add(newUser);
        saveUsersToFile();
        System.out.println("User registered: " + name);
        return newUser;
    }

    public User login(String email, String password){
        String hashedPassword = hashPassword(password);
        for(User user : users){
            if(user.getEmail().equals(email)){
                if (user.getPassword().equals(password)){
                    if (user.isActive()){
                        System.out.println("User " + user.getId() + " Logged in successful! ");
                        currentUser = user;
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

    public void logout(){
        if(currentUser != null){
            System.out.println("Goodbye " + currentUser.getName() + "!");
            currentUser = null;
        } else{
            System.out.println("No user is logged in!");
        }
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public boolean checkPermission(User user, String feature){
        if (user == null){
            System.out.println("No user logged in");
            return false;
        }
        return user.canAccess(feature);
    }

    public void changeUserRole(String email, String newRole){
        for(User user : users){
            if(user.getEmail().equals(email)){
                user.setRole(newRole);
                return;
            }
        }
        System.out.println("User not found");
    }
}