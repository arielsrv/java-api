package org.iskaypet.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import java.util.function.Function;
import org.iskaypet.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RxJavalinTest {

    @Mock
    private Javalin javalin;

    private RxJavalin rxJavalin;

    @BeforeEach
    void setUp() {
        rxJavalin = new RxJavalin(javalin);
    }

    @Test
    void create_ShouldCreateRxJavalinInstance() {
        // When
        RxJavalin result = RxJavalin.create();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJavalin()).isNotNull();
    }

    @Test
    void get_ShouldCallJavalinGetWithRxHttpHandler() {
        // Given
        String path = "/users/{id}";
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(
            new UserDTO(1L, "John", "john@example.com", "john"));

        // When
        RxJavalin result = rxJavalin.get(path, handler);

        // Then
        verify(javalin).get(eq(path), any());
        assertThat(result).isSameAs(rxJavalin);
    }

    @Test
    void post_ShouldCallJavalinPostWithRxHttpHandler() {
        // Given
        String path = "/users";
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(
            new UserDTO(1L, "John", "john@example.com", "john"));

        // When
        RxJavalin result = rxJavalin.post(path, handler);

        // Then
        verify(javalin).post(eq(path), any());
        assertThat(result).isSameAs(rxJavalin);
    }

    @Test
    void put_ShouldCallJavalinPutWithRxHttpHandler() {
        // Given
        String path = "/users/{id}";
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(
            new UserDTO(1L, "John", "john@example.com", "john"));

        // When
        RxJavalin result = rxJavalin.put(path, handler);

        // Then
        verify(javalin).put(eq(path), any());
        assertThat(result).isSameAs(rxJavalin);
    }

    @Test
    void delete_ShouldCallJavalinDeleteWithRxHttpHandler() {
        // Given
        String path = "/users/{id}";
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(
            new UserDTO(1L, "John", "john@example.com", "john"));

        // When
        RxJavalin result = rxJavalin.delete(path, handler);

        // Then
        verify(javalin).delete(eq(path), any());
        assertThat(result).isSameAs(rxJavalin);
    }

    @Test
    void patch_ShouldCallJavalinPatchWithRxHttpHandler() {
        // Given
        String path = "/users/{id}";
        Function<Context, Observable<UserDTO>> handler = context -> Observable.just(
            new UserDTO(1L, "John", "john@example.com", "john"));

        // When
        RxJavalin result = rxJavalin.patch(path, handler);

        // Then
        verify(javalin).patch(eq(path), any());
        assertThat(result).isSameAs(rxJavalin);
    }

    @Test
    void start_ShouldDelegateToJavalin() {
        // Given
        int port = 8080;
        when(javalin.start(port)).thenReturn(javalin);

        // When
        Javalin result = rxJavalin.start(port);

        // Then
        verify(javalin).start(port);
        assertThat(result).isEqualTo(javalin);
    }

    @Test
    void start_WithoutPort_ShouldDelegateToJavalin() {
        // Given
        when(javalin.start()).thenReturn(javalin);

        // When
        Javalin result = rxJavalin.start();

        // Then
        verify(javalin).start();
        assertThat(result).isEqualTo(javalin);
    }

    @Test
    void start_WithHostAndPort_ShouldDelegateToJavalin() {
        // Given
        String host = "localhost";
        int port = 8080;
        when(javalin.start(host, port)).thenReturn(javalin);

        // When
        Javalin result = rxJavalin.start(host, port);

        // Then
        verify(javalin).start(host, port);
        assertThat(result).isEqualTo(javalin);
    }

    @Test
    void stop_ShouldDelegateToJavalin() {
        // Given
        when(javalin.stop()).thenReturn(javalin);

        // When
        Javalin result = rxJavalin.stop();

        // Then
        verify(javalin).stop();
        assertThat(result).isEqualTo(javalin);
    }

    @Test
    void getJavalin_ShouldReturnWrappedJavalin() {
        // When
        Javalin result = rxJavalin.getJavalin();

        // Then
        assertThat(result).isEqualTo(javalin);
    }
}
