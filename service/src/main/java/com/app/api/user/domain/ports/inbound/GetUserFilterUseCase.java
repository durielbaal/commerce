package com.app.api.user.domain.ports.inbound;

import com.app.api.user.domain.model.UserFilter;
import com.app.shared.domain.ports.inbound.UseCase;
import reactor.core.publisher.Mono;

public interface GetUserFilterUseCase  extends UseCase<UserFilter, Mono<String>> {

}
