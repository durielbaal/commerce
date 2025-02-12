package com.app.api.user.domain.model;

public class UserFilter {
  private String user;
  private String password;

  public UserFilter(String user, String password) {
    this.user = user;
    this.password = password;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }
}
