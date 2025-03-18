package com.rapidrescue.ambulancewale.models.request;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
