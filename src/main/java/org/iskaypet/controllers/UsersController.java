package org.iskaypet.controllers;

import com.google.inject.Inject;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import org.iskaypet.dto.UserDTO;
import org.iskaypet.services.UsersService;

public class UsersController {

    private final UsersService usersService;

    @Inject
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public Observable<UserDTO> getUserById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        return usersService.getUserById(id);
    }

    public Observable<UserDTO> getUserByUsername(Context ctx) {
        String username = ctx.pathParam("username");
        return usersService.getUserByUsername(username);
    }
}
