package com.app.shared.domain.ports.inbound;

@FunctionalInterface
public interface UseCase <I,R>{
  R execute(I input);
}
