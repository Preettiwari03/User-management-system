package com.ums.service.impl;


import com.ums.entity.User;
import com.ums.exception.InvalidEmailFormatException;
import com.ums.exception.InvalidPhoneNumberException;
import com.ums.exception.UserAlreadyExistsException;
import com.ums.exception.UserNotFoundException;
import com.ums.repository.UserRepository;
import com.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String PHONE_REGEX = "^\\d{10}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public User saveUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        validateUser(user);
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private void validateUser(User user) {
        // Validate phone number
        if (!Pattern.matches(PHONE_REGEX, user.getPhoneNumber())) {
            throw new InvalidPhoneNumberException("Phone number must be exactly 10 digits.");
        }

        // Validate email format
        if (!Pattern.matches(EMAIL_REGEX, user.getEmail())) {
            throw new InvalidEmailFormatException("Email format is invalid.");
        }

        // Check for duplicate phone numbers or emails
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User with this phone number already exists.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }
    }
}