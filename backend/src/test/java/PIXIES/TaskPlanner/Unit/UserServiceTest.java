package PIXIES.TaskPlanner.Unit;

import PIXIES.TaskPlanner.Entity.User;
import PIXIES.TaskPlanner.Repository.UserRepository;
import PIXIES.TaskPlanner.Services.UserService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserById() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.findUserById(1L);

        assertNotNull(found);
        assertEquals(user.getId(), found.getId());
        assertEquals(user.getFirstName(), found.getFirstName());
        assertEquals(user.getLastName(), found.getLastName());
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    public void testCreateUser() {
        User user = new User(null, "Jane", "Doe", "jane.doe@example.com");
        when(userRepository.save(user)).thenReturn(new User(1L, "Jane", "Doe", "jane.doe@example.com"));

        User created = userService.createUser(user);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Jane", created.getFirstName());
        assertEquals("Doe", created.getLastName());
        assertEquals("jane.doe@example.com", created.getEmail());
    }

    @Test
    public void testFindAllUsers() {
        User user1 = new User(1L, "John", "Doe", "john.doe@example.com");
        User user2 = new User(2L, "Jane", "Doe", "jane.doe@example.com");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAllUsers();

        assertEquals(2, foundUsers.size());
        assertEquals("John", foundUsers.get(0).getFirstName());
        assertEquals("Jane", foundUsers.get(1).getFirstName());
    }
}
