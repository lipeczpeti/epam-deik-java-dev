import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.domain.entity.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    /**
     * BeforeEach -> lefuttatja ez a függvényt még mielőtt a testeket lefuttatni
     * initMocks -> Megnézi hogy itt a classon belül milyen @Mock annotációval rendelkező
     *              komponensek vannak (userRepository), és gyárt belőle egy mock objectumot.
     * Mock objektum -> Olyan objektum ami ugyanolyan függvényekkel rendelkezik mint az eredeti
     *                  objektum viszont az eredeti funkcionalitása helyet beégetett fix objektumokat
     *                  return-öl.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginAdmin_goodCredentials() {
        String username = "admin";
        String password = "admin";
        boolean expectedResult = true;

        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        user.setEmail("testme@gmail.com");
        user.setId(1);
        user.setRole(Role.ADMIN);

        when(userRepository.findByNameAndPassword(eq(username), eq(password))).thenReturn(Optional.of(user));

        boolean actualResult = underTest.loginAdmin(username, password);

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertTrue(underTest.getLoggedInUser().isPresent());
        Assertions.assertEquals("admin", underTest.getLoggedInUser().get().getName());
        Assertions.assertEquals("testme@gmail.com", underTest.getLoggedInUser().get().getEmail());
        Assertions.assertEquals(1, underTest.getLoggedInUser().get().getId());
        Assertions.assertEquals(Role.ADMIN, underTest.getLoggedInUser().get().getRole());
    }

    @Test
    public void testLoginAdmin_badCredentials() {
        String username = "admin2";
        String password = "admin2";
        boolean expectedResult = false;

        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);

        when(userRepository.findByNameAndPassword(eq("admin"), eq("admin"))).thenReturn(Optional.of(user));

        boolean actualResult = underTest.loginAdmin(username, password);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLoginAdmin_goodCredentialsButNotAdmin() {
        String username = "lajos";
        String password = "lajos";
        boolean expectedResult = false;

        User user = new User();
        user.setName("lajos");
        user.setPassword("lajos");
        user.setRole(Role.USER);

        when(userRepository.findByNameAndPassword(eq(username), eq(password))).thenReturn(Optional.of(user));

        boolean actualResult = underTest.loginAdmin(username, password);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLogin_badCredentials() {
        String username = "username";
        String password = "password";
        boolean expectedOptionalIsPresent = false;

        when(userRepository.findByNameAndPassword(eq(username), eq(password))).thenReturn(Optional.empty());

        Optional<UserDto> result = underTest.findByNameAndPassword(username, password);

        Assertions.assertEquals(expectedOptionalIsPresent, result.isPresent());
    }

    @Test
    public void testLogin_goodCredentials() {
        String username = "username";
        String password = "password";
        boolean expectedOptionalIsPresent = true;

        User user = new User();
        user.setId(1);
        user.setEmail("testme@gmail.com");
        user.setName(username);
        user.setPassword(password);
        user.setRole(Role.USER);

        when(userRepository.findByNameAndPassword(eq(username), eq(password))).thenReturn(Optional.of(user));

        Optional<UserDto> result = underTest.findByNameAndPassword(username, password);

        Assertions.assertEquals(expectedOptionalIsPresent, result.isPresent());
        Assertions.assertEquals(user.getId(), result.get().getId());
        Assertions.assertEquals(user.getEmail(), result.get().getEmail());
        Assertions.assertEquals(user.getRole(), result.get().getRole());
        Assertions.assertEquals(user.getName(), result.get().getName());
    }

    @Test
    public void testLogout() {
        String username = "admin";
        String password = "admin";

        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        user.setEmail("testme@gmail.com");
        user.setId(1);
        user.setRole(Role.ADMIN);

        when(userRepository.findByNameAndPassword(eq(username), eq(password))).thenReturn(Optional.of(user));

        underTest.loginAdmin(username, password);
        underTest.logout();

        Assertions.assertFalse(underTest.getLoggedInUser().isPresent());
    }
}
