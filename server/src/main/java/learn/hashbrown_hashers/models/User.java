package learn.hashbrown_hashers.models;

import java.util.Objects;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String passwordHash;
    private String email;
    private int roleId;

    public User(String lastName, String firstName, String userName, String passwordHash, String email, int roleId) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.roleId = roleId;
    }

    public User(){

    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId && roleId == user.roleId && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(passwordHash, user.passwordHash) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, userName, passwordHash, email, roleId);
    }
}
