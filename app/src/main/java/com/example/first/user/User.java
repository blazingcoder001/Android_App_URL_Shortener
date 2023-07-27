package com.example.first.user;

public class User {
    String username;
    String password,firstname,lastname,url_Full,url_shorten;

    public String getUrl_shorten() {
        return url_shorten;
    }

    public void setUrl_shorten(String url_shorten) {
        this.url_shorten = url_shorten;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUrl_Full() {
        return url_Full;
    }

    public void setUrl_Full(String url_Full) {
        this.url_Full = url_Full;
    }
}
