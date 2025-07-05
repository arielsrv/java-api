package org.iskaypet.config;

import com.google.inject.AbstractModule;
import org.iskaypet.clients.UsersClient;
import org.iskaypet.clients.UsersClientImpl;
import org.iskaypet.services.UsersService;
import org.iskaypet.services.UsersServiceImpl;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind UsersClient interface to its implementation
        bind(UsersClient.class).to(UsersClientImpl.class);

        // Bind UsersService interface to its implementation
        bind(UsersService.class).to(UsersServiceImpl.class);
    }
}
