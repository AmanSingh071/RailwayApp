package com.example.railway.activities.Models;

public class users {
    String email;
    String password;
    String username;

    public users(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public users(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
