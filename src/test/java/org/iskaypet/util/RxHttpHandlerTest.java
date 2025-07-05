package org.iskaypet.util;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import java.util.Map;
import java.util.function.Function;
import org.iskaypet.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RxHttpHandlerTest {

    @Mock
    private Context ctx;

    private RxHttpHandler rxHttpHandler;

    @BeforeEach
    void setUp() {
        rxHttpHandler = new RxHttpHandler();
    }

    @Test
    void auto_WithValidUser_ShouldSet200StatusAndJson() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john@example.com", "johndoe");
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(userDTO);

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).json(userDTO);
        verify(ctx).status(200);
        verifyNoMoreInteractions(ctx);
    }

    @Test
    void auto_WithNullUser_ShouldSet404StatusAndErrorJson() throws Exception {
        // Given
        Function<Context, Observable<UserDTO>> handler = context -> Observable.empty();

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).status(404);
        verify(ctx).json(Map.of("error", "Not found"));
        verifyNoMoreInteractions(ctx);
    }

    @Test
    void auto_WithError_ShouldSet500StatusAndErrorJson() throws Exception {
        // Given
        RuntimeException error = new RuntimeException("Database error");
        Function<Context, Observable<UserDTO>> handler = context -> Observable.error(error);

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).status(500);
        verify(ctx).json(Map.of("error", "Database error"));
        verifyNoMoreInteractions(ctx);
    }

    @Test
    void auto_WithEmptyObservable_ShouldSet404StatusAndErrorJson() throws Exception {
        // Given
        Function<Context, Observable<UserDTO>> handler = context -> Observable.empty();

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).status(404);
        verify(ctx).json(Map.of("error", "Not found"));
        verifyNoMoreInteractions(ctx);
    }

    @Test
    void auto_WithMultipleValues_ShouldHandleFirstValue() throws Exception {
        // Given
        UserDTO user1 = new UserDTO(1L, "John Doe", "john@example.com", "johndoe");
        UserDTO user2 = new UserDTO(2L, "Jane Smith", "jane@example.com", "janesmith");
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(user1, user2);

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).json(user1); // Should handle first value only
        verify(ctx).status(200);
        verifyNoMoreInteractions(ctx);
    }

    @Test
    void auto_WithComplexError_ShouldSet500StatusAndErrorJson() throws Exception {
        // Given
        IllegalStateException error = new IllegalStateException("Complex error message");
        Function<Context, Observable<UserDTO>> handler = context -> Observable.error(error);

        // When
        RxHttpHandler.auto(handler).handle(ctx);

        // Then
        verify(ctx).status(500);
        verify(ctx).json(Map.of("error", "Complex error message"));
        verifyNoMoreInteractions(ctx);
    }
}
