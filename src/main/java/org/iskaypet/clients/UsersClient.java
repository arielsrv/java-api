package org.iskaypet.clients;

import io.reactivex.rxjava3.core.Observable;
import org.iskaypet.clients.responses.UserResponse;

public interface UsersClient {

    Observable<UserResponse> getUserById(Long id);

    Observable<UserResponse> getUserByUsername(String username);
}
