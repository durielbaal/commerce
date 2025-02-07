package com.app.api.shared.domain.ports.inbound;

@FunctionalInterface
public interface UseCase <I,R>{
  R execute(I input);
}
