package com.alextayron.schedule_finance.user.service;

import com.alextayron.schedule_finance.user.controller.CreateUserDto;
import com.alextayron.schedule_finance.user.controller.UpdateUserDto;
import com.alextayron.schedule_finance.user.entity.User;
import com.alextayron.schedule_finance.user.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789"
            );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.name(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.phone(), userCaptured.getPhone());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {
        @Test
        @DisplayName("Should get user by id whith success when Optional is present")
        void shouldGetUserByIdSuccessIsPresent(){

            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserId().toString());

            // Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id whith success when Optional is empty")
        void shouldGetUserByIdSuccessIsEmpty(){

            // Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional
                    .empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor
                            .capture());

            //Act
            var output = userService.getUserById(userId.toString());

            // Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers{
        @Test
        @DisplayName("Should return all user whit success")
        void shouldReturnAllUserWithSuccess() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(List.of(user)).when(userRepository).findAll();

            //Act
            var output = userService.listUsers();

            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());

        }
    }

    @Nested
    class deleteById{
        @Test
        @DisplayName("Should delete user with success when user exist")
        void shouldDeleteUserWithSuccessWhenUserExist() {

            // Arrange
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteById(userId.toString());

            // Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Should not delete user when user not exist")
        void shouldNotDeleteUserWhenUserNotExist() {

            // Arrange
            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteById(userId.toString());

            // Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());

        }
    }

    @Nested
    class updateUser{
        @Test
        @DisplayName("Should update user by id when user exist and username and password is filled")
        void shouldUpdateUserByIdWhenUserExistAndUsernameAndPasswordIsFilled() {

            // Arrange
            var updateUserDto = new UpdateUserDto(
              "newUsername",
                    "NewPhone",
                    "newPassWord"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "316497643197",
                    "123456789",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            // Act
            userService.updateUserById(user.getUserId().toString(), updateUserDto);

            // Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDto.name(), userCaptured.getUsername());
            assertEquals(updateUserDto.phone(), userCaptured.getPhone());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);

        }

        @Test
        @DisplayName("Should not update user by id when user not exist")
        void shouldNotUpdateUserByIdWhenUserNotExist() {

            // Arrange
            var updateUserDto = new UpdateUserDto(
                    "newUsername",
                    "NewPhone",
                    "newPassword"
            );
            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            // Act
            userService.updateUserById(userId.toString(), updateUserDto);

            // Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());

        }
    }
}
