package by.ipps.admin.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {
    private String login;
    private String hashPassword;
    private String name;
    private String surName;
    private String patronicName;
    private List<String> roles;
    private String position;
    private String email;
    private long department;
    private boolean enabled;
    private boolean block;
    private Date dateLastChangePassword;

    public User(String name, String username, String patronic, List<String> roles) {
        this.name = name;
        this.surName = username;
        this.patronicName = patronic;
        this.roles = roles;
    }
}
