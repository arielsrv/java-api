package org.iskaypet.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.iskaypet.clients.UsersClient;
import org.iskaypet.clients.responses.UserResponse;
import org.iskaypet.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersClient usersClient;

    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        usersService = new UsersServiceImpl(usersClient);
    }

    @Test
    void getUserById_ShouldReturnUserDTO() {
        // Given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(
            userId, "John Doe", "john@example.com", "johndoe", "+1234567890", "https://johndoe.com"
        );
        when(usersClient.getUserById(userId)).thenReturn(Observable.just(userResponse));

        // When
        Observable<UserDTO> result = usersService.getUserById(userId);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserDTO userDTO = testObserver.values().get(0);
        assertThat(userDTO.getId()).isEqualTo(userId);
        assertThat(userDTO.getName()).isEqualTo("John Doe");
        assertThat(userDTO.getEmail()).isEqualTo("john@example.com");
        assertThat(userDTO.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void getUserByUsername_ShouldReturnUserDTO() {
        // Given
        String username = "johndoe";
        UserResponse userResponse = new UserResponse(
            1L, "John Doe", "john@example.com", username, "+1234567890", "https://johndoe.com"
        );
        when(usersClient.getUserByUsername(username)).thenReturn(Observable.just(userResponse));

        // When
        Observable<UserDTO> result = usersService.getUserByUsername(username);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserDTO userDTO = testObserver.values().get(0);
        assertThat(userDTO.getId()).isEqualTo(1L);
        assertThat(userDTO.getName()).isEqualTo("John Doe");
        assertThat(userDTO.getEmail()).isEqualTo("john@example.com");
        assertThat(userDTO.getUsername()).isEqualTo(username);
    }

    @Test
    void getUserById_WhenClientThrowsError_ShouldReturnNull() {
        // Given
        Long userId = 1L;
        when(usersClient.getUserById(userId)).thenReturn(Observable.empty());

        // When
        Observable<UserDTO> result = usersService.getUserById(userId);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    void getUserByUsername_WhenClientThrowsError_ShouldReturnNull() {
        // Given
        String username = "johndoe";
        when(usersClient.getUserByUsername(username)).thenReturn(Observable.empty());

        // When
        Observable<UserDTO> result = usersService.getUserByUsername(username);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }

    @Test
    void getUserById_ShouldMapUserResponseToUserDTO() {
        // Given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(
            userId, "Jane Smith", "jane@example.com", "janesmith", "+9876543210", "https://janesmith.com"
        );
        when(usersClient.getUserById(userId)).thenReturn(Observable.just(userResponse));

        // When
        Observable<UserDTO> result = usersService.getUserById(userId);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        UserDTO userDTO = testObserver.values().get(0);
        
        // Verify mapping
        assertThat(userDTO.getId()).isEqualTo(userResponse.getId());
        assertThat(userDTO.getName()).isEqualTo(userResponse.getName());
        assertThat(userDTO.getEmail()).isEqualTo(userResponse.getEmail());
        assertThat(userDTO.getUsername()).isEqualTo(userResponse.getUsername());
    }

    @Test
    void getUserByUsername_ShouldMapUserResponseToUserDTO() {
        // Given
        String username = "bobwilson";
        UserResponse userResponse = new UserResponse(
            2L, "Bob Wilson", "bob@example.com", username, "+5551234567", "https://bobwilson.com"
        );
        when(usersClient.getUserByUsername(username)).thenReturn(Observable.just(userResponse));

        // When
        Observable<UserDTO> result = usersService.getUserByUsername(username);

        // Then
        TestObserver<UserDTO> testObserver = result.test();
        UserDTO userDTO = testObserver.values().get(0);
        
        // Verify mapping
        assertThat(userDTO.getId()).isEqualTo(userResponse.getId());
        assertThat(userDTO.getName()).isEqualTo(userResponse.getName());
        assertThat(userDTO.getEmail()).isEqualTo(userResponse.getEmail());
        assertThat(userDTO.getUsername()).isEqualTo(userResponse.getUsername());
    }
} 