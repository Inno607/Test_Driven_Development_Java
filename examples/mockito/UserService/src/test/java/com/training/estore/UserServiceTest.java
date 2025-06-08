package com.training.estore;

import com.training.estore.dao.UsersRepository;
import com.training.estore.exceptions.EmailNotificationServiceException;
import com.training.estore.exceptions.UserServiceException;
import com.training.estore.model.User;
import com.training.estore.service.Impl.EmailVerificationServiceImpl;
import com.training.estore.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     *  N.B: We inject the implementation class as a 'mock'
     *   + And we provide the dependecies to the service class by annotating with '@Mock'
     *   + By design Mockito will inject the 'Mock' dependencies to the main service annotated '@InjectMocks'
     */
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Inno607";
        lastName  = "io";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Arrange
        /**
         * Lets understand / elaborate the syntax below
         *   + we tell Mockito that 'when' save method in userRepository is called it should return true
         *   + this is the behaviour we gave to the method in our code
         */
        Mockito.when(usersRepository.save(any(User.class))).thenReturn(true);

        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect.");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertNotNull(user.getId(), "User id is missing");

        /**
         *  We need to insure that the save method was called during execution and we use 'Mockito.verify' to do that
         *  Here is the code below
         */
        Mockito.verify(usersRepository)
                .save(any(User.class));
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String firstName = "";
        String expectedExceptionMessage = "User's first name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Empty first name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage,thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("Empty last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String lastName = "";
        String expectedExceptionMessage = "User's last name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage,thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");
    }


    /**
     * STABBING VOID METHODS
     */
    @Test
    @DisplayName("EmailNotificationException is handled")
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        // Act & Assert
        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

        // Assert
        verify(emailVerificationService, times(1)).
                scheduleEmailConfirmation(any(User.class));

    }

    /**
     *  INVOKING REAL METHOD USING MOCKITO
     */
    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        //NB: calling real method instead of mock object
        doCallRealMethod().when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        verify(emailVerificationService, times(1))
                .scheduleEmailConfirmation(any(User.class));
    }


}
