package kz.yerkebulan.oopendtermproject.patterns.builder;

import kz.yerkebulan.oopendtermproject.dto.User;

import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    private String username;
    private String email;
    private String password;
    private Integer age;
    private Double weight;
    private Double height;

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public UserBuilder weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public UserBuilder height(Double height) {
        this.height = height;
        return this;
    }

    public User build() {
        validateRequiredFields();

        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    private void validateRequiredFields() {
        if (username == null) throw new IllegalStateException("Username is required");
        if (email == null) throw new IllegalStateException("Email is required");
        if (password == null) throw new IllegalStateException("Password is required");
    }

    private Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("age", age);
        params.put("height", height);
        params.put("weight", weight);
        params.put("email", email);
        params.put("password", password);
        return params;
    }
}
