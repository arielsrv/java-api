package org.iskaypet.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.iskaypet.config.AppModule;
import org.iskaypet.controllers.UsersController;
import org.iskaypet.dto.UserDTO;
import org.iskaypet.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFlowIntegrationTest {

    private Injector injector;
    private UsersController usersController;
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new AppModule());
        usersController = injector.getInstance(UsersController.class);
        usersService = injector.getInstance(UsersService.class);
    }

    @Test
    void completeFlow_GetUserById_ShouldWorkEndToEnd() {
        // Given
        Long userId = 1L;

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
        assertThat(userDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDTO.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void completeFlow_GetUserByUsername_ShouldWorkEndToEnd() {
        // Given
        String username = "johndoe";

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
        assertThat(userDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDTO.getUsername()).isEqualTo(username);
    }

    @Test
    void dependencyInjection_ShouldWorkCorrectly() {
        // Given & When
        UsersController controller = injector.getInstance(UsersController.class);
        UsersService service = injector.getInstance(UsersService.class);

        // Then
        assertThat(controller).isNotNull();
        assertThat(service).isNotNull();
        assertThat(controller).isNotSameAs(usersController); // Different instances
        assertThat(service).isNotSameAs(usersService); // Different instances
    }

    @Test
    void multipleRequests_ShouldReturnConsistentResults() {
        // Given
        Long userId = 1L;

        // When
        Observable<UserDTO> result1 = usersService.getUserById(userId);
        Observable<UserDTO> result2 = usersService.getUserById(userId);

        // Then
        TestObserver<UserDTO> testObserver1 = result1.test();
        TestObserver<UserDTO> testObserver2 = result2.test();

        testObserver1.assertComplete();
        testObserver2.assertComplete();

        UserDTO user1 = testObserver1.values().get(0);
        UserDTO user2 = testObserver2.values().get(0);

        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
    }

    @Test
    void differentUserIds_ShouldReturnDifferentResults() {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;

        // When
        Observable<UserDTO> result1 = usersService.getUserById(userId1);
        Observable<UserDTO> result2 = usersService.getUserById(userId2);

        // Then
        TestObserver<UserDTO> testObserver1 = result1.test();
        TestObserver<UserDTO> testObserver2 = result2.test();

        testObserver1.assertComplete();
        testObserver2.assertComplete();

        UserDTO user1 = testObserver1.values().get(0);
        UserDTO user2 = testObserver2.values().get(0);

        assertThat(user1.getId()).isEqualTo(userId1);
        assertThat(user2.getId()).isEqualTo(userId2);
    }
}
