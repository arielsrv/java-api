package org.iskaypet.services;

import io.reactivex.rxjava3.core.Observable;
import org.iskaypet.dto.UserDTO;

public interface UsersService {

    Observable<UserDTO> getUserById(Long id);

    Observable<UserDTO> getUserByUsername(String username);
}

