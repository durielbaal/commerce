package com.app.api.user.infrastructure.repository.postgres.entity;


import com.app.api.user.domain.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("Users")
public class UserEntity {
  @Id
  private Long id;
  private String username;
  private String password;
  private String role;

  public User entityToModel(){
    return new User(this.username,this.password);
  }

}
