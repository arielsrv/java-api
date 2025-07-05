package org.iskaypet.util;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import java.util.function.Function;

public class RxJavalin {

    private final Javalin javalin;

    public RxJavalin(Javalin javalin) {
        this.javalin = javalin;
    }

    public static RxJavalin create() {
        return new RxJavalin(Javalin.create());
    }

    public static RxJavalin create(
        java.util.function.Consumer<io.javalin.config.JavalinConfig> config) {
        return new RxJavalin(Javalin.create(config));
    }

    public <T> RxJavalin get(String path, Function<Context, Observable<T>> handler) {
        javalin.get(path, RxHttpHandler.auto(handler));
        return this;
    }

    public <T> RxJavalin post(String path, Function<Context, Observable<T>> handler) {
        javalin.post(path, RxHttpHandler.auto(handler));
        return this;
    }

    public <T> RxJavalin put(String path, Function<Context, Observable<T>> handler) {
        javalin.put(path, RxHttpHandler.auto(handler));
        return this;
    }

    public <T> RxJavalin delete(String path, Function<Context, Observable<T>> handler) {
        javalin.delete(path, RxHttpHandler.auto(handler));
        return this;
    }

    public <T> RxJavalin patch(String path, Function<Context, Observable<T>> handler) {
        javalin.patch(path, RxHttpHandler.auto(handler));
        return this;
    }

    // Delegate other methods to the wrapped Javalin instance
    public Javalin start() {
        return javalin.start();
    }

    public Javalin start(int port) {
        return javalin.start(port);
    }

    public Javalin start(String host, int port) {
        return javalin.start(host, port);
    }

    public Javalin stop() {
        return javalin.stop();
    }

    public Javalin getJavalin() {
        return javalin;
    }
}
