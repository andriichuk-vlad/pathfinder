package com.xelerit.pathfinder.service.User;

import com.xelerit.pathfinder.model.User;
import com.xelerit.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id: " + id + " not found")
        );
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email: " + user.getEmail() + " already exists");
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
            return user;
        }
        throw new RuntimeException("User with email: " + user.getEmail() + " does not exist");
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " not found")
        );
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
