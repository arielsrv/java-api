package org.iskaypet.clients;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.iskaypet.clients.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UsersClientImplTest {

    private UsersClientImpl usersClient;

    @BeforeEach
    void setUp() {
        usersClient = new UsersClientImpl();
    }

    @Test
    void getUserById_ShouldReturnUserResponse() {
        // Given
        Long userId = 1L;

        // When
        Observable<UserResponse> result = usersClient.getUserById(userId);

        // Then
        TestObserver<UserResponse> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserResponse userResponse = testObserver.values().get(0);
        assertThat(userResponse.getId()).isEqualTo(userId);
        assertThat(userResponse.getName()).isEqualTo("John Doe");
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userResponse.getUsername()).isEqualTo("johndoe");
        assertThat(userResponse.getPhone()).isEqualTo("+1234567890");
        assertThat(userResponse.getWebsite()).isEqualTo("https://johndoe.com");
    }

    @Test
    void getUserByUsername_ShouldReturnUserResponse() {
        // Given
        String username = "testuser";

        // When
        Observable<UserResponse> result = usersClient.getUserByUsername(username);

        // Then
        TestObserver<UserResponse> testObserver = result.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);

        UserResponse userResponse = testObserver.values().get(0);
        assertThat(userResponse.getId()).isEqualTo(1L);
        assertThat(userResponse.getName()).isEqualTo("John Doe");
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userResponse.getUsername()).isEqualTo(username);
        assertThat(userResponse.getPhone()).isEqualTo("+1234567890");
        assertThat(userResponse.getWebsite()).isEqualTo("https://johndoe.com");
    }

    @Test
    void getUserById_WithDifferentIds_ShouldReturnDifferentResponses() {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;

        // When
        Observable<UserResponse> result1 = usersClient.getUserById(userId1);
        Observable<UserResponse> result2 = usersClient.getUserById(userId2);

        // Then
        TestObserver<UserResponse> testObserver1 = result1.test();
        TestObserver<UserResponse> testObserver2 = result2.test();

        testObserver1.assertComplete();
        testObserver2.assertComplete();

        UserResponse user1 = testObserver1.values().get(0);
        UserResponse user2 = testObserver2.values().get(0);

        assertThat(user1.getId()).isEqualTo(userId1);
        assertThat(user2.getId()).isEqualTo(userId2);
    }

    @Test
    void getUserByUsername_WithDifferentUsernames_ShouldReturnDifferentResponses() {
        // Given
        String username1 = "user1";
        String username2 = "user2";

        // When
        Observable<UserResponse> result1 = usersClient.getUserByUsername(username1);
        Observable<UserResponse> result2 = usersClient.getUserByUsername(username2);

        // Then
        TestObserver<UserResponse> testObserver1 = result1.test();
        TestObserver<UserResponse> testObserver2 = result2.test();

        testObserver1.assertComplete();
        testObserver2.assertComplete();

        UserResponse user1 = testObserver1.values().get(0);
        UserResponse user2 = testObserver2.values().get(0);

        assertThat(user1.getUsername()).isEqualTo(username1);
        assertThat(user2.getUsername()).isEqualTo(username2);
    }
} 