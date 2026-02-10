package kz.yerkebulan.oopendtermproject.service;

import kz.yerkebulan.oopendtermproject.dto.User;
import kz.yerkebulan.oopendtermproject.exception.UserNotFoundException;
import kz.yerkebulan.oopendtermproject.patterns.builder.UserBuilder;
import kz.yerkebulan.oopendtermproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_Success() {
        User user = new UserBuilder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        User savedUser = new UserBuilder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        when(userRepository.save(user)).thenReturn(savedUser);

        // Act
        User result = userService.create(user);

        // Assert
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getById(100L);
        });
    }
}
