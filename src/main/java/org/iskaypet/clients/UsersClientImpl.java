package org.iskaypet.clients;

import io.reactivex.rxjava3.core.Observable;
import org.iskaypet.clients.responses.UserResponse;

public class UsersClientImpl implements UsersClient {
    
    @Override
    public Observable<UserResponse> getUserById(Long id) {
        return Observable.fromCallable(() -> {
            // Simulando llamada asíncrona a API externa
            Thread.sleep(100); // Simulando delay de red
            return new UserResponse(
                id,
                "John Doe",
                "john.doe@example.com",
                "johndoe",
                "+1234567890",
                "https://johndoe.com"
            );
        });
    }
    
    @Override
    public Observable<UserResponse> getUserByUsername(String username) {
        return Observable.fromCallable(() -> {
            // Simulando llamada asíncrona a API externa
            Thread.sleep(100); // Simulando delay de red
            return new UserResponse(
                1L,
                "John Doe",
                "john.doe@example.com",
                username,
                "+1234567890",
                "https://johndoe.com"
            );
        });
    }
} 