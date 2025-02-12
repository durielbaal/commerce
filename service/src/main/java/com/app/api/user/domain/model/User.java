package com.app.api.user.domain.model;

public class User {
  private String username;
  private String password;

  public User(String password, String username) {
    this.password = password;
    this.username = username;
  }

  public User(){}

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
}
