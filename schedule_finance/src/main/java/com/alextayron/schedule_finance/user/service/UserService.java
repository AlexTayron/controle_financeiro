package com.alextayron.schedule_finance.user.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alextayron.schedule_finance.user.repository.UserRepository;
import com.alextayron.schedule_finance.user.controller.CreateUserDto;
import com.alextayron.schedule_finance.user.controller.UpdateUserDto;
import com.alextayron.schedule_finance.user.entity.User;;

@Service
public class UserService {

    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.name(),
                createUserDto.email(),
                createUserDto.phone(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {

        return userRepository.findById(UUID.fromString(userId));

    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.name() != null) {
                user.setUsername(updateUserDto.name());
            }
            if (updateUserDto.phone() != null) {
                user.setPhone(updateUserDto.phone());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExistent = userRepository.existsById(id);

        if (userExistent) {
            userRepository.deleteById(id);
        }
    }
}
