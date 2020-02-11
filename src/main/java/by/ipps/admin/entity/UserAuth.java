package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
  private long id;
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
}
