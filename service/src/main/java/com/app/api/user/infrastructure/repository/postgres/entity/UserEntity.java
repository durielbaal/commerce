package com.app.api.user.infrastructure.repository.postgres.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("Users")
public class UserEntity {
  @Id
  private Long id;
  private String username;
  private String password;
  private String role;

  public UserEntity() {
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
