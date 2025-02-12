package com.app.api.user.rest.adapter;

import com.app.api.user.domain.model.UserFilter;

public class UserFilterAdapter {
  public static UserFilter adapt(String username, String password){
    return new UserFilter(username,password);
  }
}
