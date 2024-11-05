package org.example.Classes;

public class User {
    private int _id;
    private String _username;
    private String _password;
    private String _role;

    User() {
        _id = 0;
        _username = "";
        _password = "";
        _role = "";
    }

    User(int id, String username, String password, String role) {
        _id = id; _username = username; _password = password; _role = role;
    }

    public void setId(int id) { _id = id; }
    public void setUsername(String username) { _username = username; }
    public void setPassword(String password) { _password = password; }
    public void setRole(String role) { _role = role; }

    public int getId() { return _id; }
    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public String getRole() { return _role; }
}
