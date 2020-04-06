package com.example.hyperone.Model;

public class UserData {

    String email, data;

    public UserData(String email, String data) {
        this.email = email;
        this.data = data;
    }

    public UserData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
