package org.iskaypet.util;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.reactivex.rxjava3.core.Observable;
import java.util.Map;
import java.util.function.Function;

public class RxHttpHandler {

    public static <T> Handler auto(Function<Context, Observable<T>> obsProvider) {
        return ctx -> {
            obsProvider.apply(ctx)
                .firstElement()
                .subscribe(
                    result -> {
                        ctx.status(200);
                        ctx.json(result);
                    },
                    error -> {
                        ctx.status(500);
                        ctx.json(Map.of("error", error.getMessage()));
                    },
                    () -> {
                        ctx.status(404);
                        ctx.json(Map.of("error", "Not found"));
                    }
                );
        };
    }
}
