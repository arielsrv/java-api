package org.iskaypet.services;

import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;
import org.iskaypet.clients.UsersClient;
import org.iskaypet.dto.UserDTO;
import org.iskaypet.clients.responses.UserResponse;

public class UsersServiceImpl implements UsersService {

    private final UsersClient usersClient;

    @Inject
    public UsersServiceImpl(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Override
    public Observable<UserDTO> getUserById(Long id) {
        return usersClient.getUserById(id)
            .map(this::convertToDTO)
            .onErrorResumeNext(error -> Observable.empty());
    }

    @Override
    public Observable<UserDTO> getUserByUsername(String username) {
        return usersClient.getUserByUsername(username)
            .map(this::convertToDTO)
            .onErrorResumeNext(error -> Observable.empty());
    }

    private UserDTO convertToDTO(UserResponse response) {
        return new UserDTO(
            response.getId(),
            response.getName(),
            response.getEmail(),
            response.getUsername()
        );
    }
}
