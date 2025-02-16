package com.app.shared.domain.ports.inbound;

/**
 * A functional interface representing a generic use case with an input and an output.
 *
 * @param <I> The type of input parameter.
 * @param <R> The type of return/result value.
 */
@FunctionalInterface
public interface UseCase<I, R> {

  /**
   * Executes the use case logic with the given input and returns a result.
   *
   * @param input The input data for the use case.
   * @return The result of executing the use case.
   */
  R execute(I input);
}
