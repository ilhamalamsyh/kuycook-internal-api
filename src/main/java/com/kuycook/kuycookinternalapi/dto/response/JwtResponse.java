package com.kuycook.kuycookinternalapi.dto.response;

import java.util.List;

public class JwtResponse {
    private Long id;
    private String fullname;
    private String email;
    private List<String> roles;
    private String token;
    private String type = "Bearer";

    public JwtResponse(Long id, String fullname, String email, List<String> roles, String token) {
        this.token = token;
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
