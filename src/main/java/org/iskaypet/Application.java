package org.iskaypet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.iskaypet.config.AppModule;
import org.iskaypet.controllers.UsersController;
import org.iskaypet.util.RxJavalin;

public class Application {

    public static void main(String[] args) {
        // Create Guice injector
        Injector injector = Guice.createInjector(new AppModule());

        // Create RxJavalin app with virtual threads
        RxJavalin app = RxJavalin.create(config -> {
            config.useVirtualThreads = true;   // ← ¡aquí la magia!
            config.showJavalinBanner = false;
        });

        // Get controller from Guice
        UsersController usersController = injector.getInstance(UsersController.class);

        // Setup routes - RxHttpHandler.auto is applied automatically!
        app.get("/users/{id}", usersController::getUserById);
        app.get("/users/username/{username}", usersController::getUserByUsername);

        System.out.println("🚀 Server running on http://localhost:8080");
        System.out.println("📡 Available endpoints:");
        System.out.println("  GET /users/{id}");
        System.out.println("  GET /users/username/{username}");
        System.out.println("⚡ Using Virtual Threads + RxJava for reactive async operations!");
        System.out.println("✨ RxHttpHandler.auto applied automatically!");

        app.start(8080);
    }
}
