package model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String hashedPassword;
    private String role;
    private boolean isActive;

    public User(int id, String name, String email, String password, String hashedPassword){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.hashedPassword = hashedPassword;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public boolean isActive(){return isActive;}


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIsActive(boolean active) {
        isActive = active;
        System.out.println(getName() + " is now " + (active ? "active" : "inactive"));
    }

    public boolean canAccess(String feature){
        switch (this.role){
            case "admin":
                return true;
            case "moderator":
                return feature.equals("view") || feature.equals("edit");
            case "user":
                return feature.equals("view");
            default:
                return false;
        }
    }
}