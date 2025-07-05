package org.iskaypet.controllers;

import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.iskaypet.dto.UserDTO;
import org.iskaypet.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private Context ctx;

    private UsersController usersController;

    @BeforeEach
    void setUp() {
        usersController = new UsersController(usersService);
    }

    @Test
    void getUserById_ShouldReturnObservableWithUserDTO() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(userId, "John Doe", "john@example.com", "johndoe");
        when(ctx.pathParam("id")).thenReturn("1");
        when(usersService.getUserById(userId)).thenReturn(Observable.just(userDTO));

        // When
        Observable<UserDTO> result = usersController.getUserById(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserDTO resultUser = testObserver.values().get(0);
        assertThat(resultUser.getId()).isEqualTo(userId);
        assertThat(resultUser.getName()).isEqualTo("John Doe");
        assertThat(resultUser.getEmail()).isEqualTo("john@example.com");
        assertThat(resultUser.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void getUserByUsername_ShouldReturnObservableWithUserDTO() {
        // Given
        String username = "johndoe";
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john@example.com", username);
        when(ctx.pathParam("username")).thenReturn(username);
        when(usersService.getUserByUsername(username)).thenReturn(Observable.just(userDTO));

        // When
        Observable<UserDTO> result = usersController.getUserByUsername(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserDTO resultUser = testObserver.values().get(0);
        assertThat(resultUser.getId()).isEqualTo(1L);
        assertThat(resultUser.getName()).isEqualTo("John Doe");
        assertThat(resultUser.getEmail()).isEqualTo("john@example.com");
        assertThat(resultUser.getUsername()).isEqualTo(username);
    }

    @Test
    void getUserById_WithInvalidId_ShouldThrowNumberFormatException() {
        // Given
        when(ctx.pathParam("id")).thenReturn("invalid");

        // When & Then
        try {
            usersController.getUserById(ctx);
            // Should not reach here
            assertThat(false).isTrue();
        } catch (NumberFormatException e) {
            assertThat(e.getMessage()).contains("invalid");
        }
    }

    @Test
    void getUserById_WithNullUser_ShouldReturnObservableWithNull() {
        // Given
        Long userId = 1L;
        when(ctx.pathParam("id")).thenReturn("1");
        when(usersService.getUserById(userId)).thenReturn(Observable.empty());

        // When
        Observable<UserDTO> result = usersController.getUserById(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    void getUserByUsername_WithNullUser_ShouldReturnObservableWithNull() {
        // Given
        String username = "nonexistent";
        when(ctx.pathParam("username")).thenReturn(username);
        when(usersService.getUserByUsername(username)).thenReturn(Observable.empty());

        // When
        Observable<UserDTO> result = usersController.getUserByUsername(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    void getUserById_WithServiceError_ShouldPropagateError() {
        // Given
        Long userId = 1L;
        RuntimeException error = new RuntimeException("Service error");
        when(ctx.pathParam("id")).thenReturn("1");
        when(usersService.getUserById(userId)).thenReturn(Observable.error(error));

        // When
        Observable<UserDTO> result = usersController.getUserById(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertError(RuntimeException.class);
    }

    @Test
    void getUserByUsername_WithServiceError_ShouldPropagateError() {
        // Given
        String username = "johndoe";
        RuntimeException error = new RuntimeException("Service error");
        when(ctx.pathParam("username")).thenReturn(username);
        when(usersService.getUserByUsername(username)).thenReturn(Observable.error(error));

        // When
        Observable<UserDTO> result = usersController.getUserByUsername(ctx);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertError(RuntimeException.class);
    }
} 