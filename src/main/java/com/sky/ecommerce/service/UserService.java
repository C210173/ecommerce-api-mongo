package com.sky.ecommerce.service;

import com.sky.ecommerce.model.User;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.request.ChangePasswordRequest;
import com.sky.ecommerce.request.ChangeRoleRequest;

import java.util.List;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws UserException;

    User updateUserProfile(String jwt, User updatedUser) throws UserException;

    void changeUserPassword(String jwt, ChangePasswordRequest req) throws UserException;

    void deleteUserAccount(String userId) throws UserException;

    List<User> getAllUsers();

    User changeUserRole(ChangeRoleRequest roleReq) throws UserException;
}
