package com.sky.ecommerce.service;

import com.sky.ecommerce.config.JwtProvider;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.UserRepository;
import com.sky.ecommerce.request.ChangePasswordRequest;
import com.sky.ecommerce.request.ChangeRoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user==null){
            throw new UserException("user not found with email " + email);
        }
        return user;
    }

    @Override
    public User updateUserProfile(String jwt, User updatedUser) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found with email " + email);
        }
        if (updatedUser.getFullName()!=null){
            user.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getEmail()!=null){
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getAddress()!=null){
            user.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getPhoneNumber()!=null){
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getImageUrl()!=null){
            user.setImageUrl(updatedUser.getImageUrl());
        }
        if (updatedUser.getPaymentDetails()!=null){
            user.setPaymentDetails(updatedUser.getPaymentDetails());
        }
        return userRepository.save(user);
    }

    @Override
    public void changeUserPassword(String jwt, ChangePasswordRequest req) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found with email " + email);
        }

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new UserException("Incorrect current password");
        }

        String encryptedNewPassword = passwordEncoder.encode(req.getNewPassword());
        user.setPassword(encryptedNewPassword);

        userRepository.save(user);
    }

    @Override
    public void deleteUserAccount(String userId) throws UserException {
        User userToDelete = userRepository.findById(userId).orElse(null);

        if (userToDelete == null) {
            throw new UserException("User not found with ID: " + userId);
        }

        userRepository.delete(userToDelete);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User changeUserRole(ChangeRoleRequest roleReq) throws UserException {
        Optional<User> userOptional = userRepository.findById(roleReq.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(roleReq.getRole());
            return userRepository.save(user);
        } else {
            throw new UserException("User not found with ID: " + roleReq.getUserId());
        }
    }

}
