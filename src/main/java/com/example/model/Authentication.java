package com.example.model;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Value;

@SpringComponent
@VaadinSessionScope
public class Authentication {

    @Value("${aut.name}")
    private String username;
    @Value("${aut.password}")
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean authenticate(String username, String password) {


        if (username.equals(getUsername()) && password.equals(getPassword())) {
            return true;
        }
        return false;
    }

}