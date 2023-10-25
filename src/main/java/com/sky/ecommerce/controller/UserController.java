package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Role;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.ChangePasswordRequest;
import com.sky.ecommerce.request.ChangeRoleRequest;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUserHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt, @RequestBody User updatedUser) throws UserException {
        User user = userService.updateUserProfile(jwt, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/change-role")
    public ResponseEntity<User> changeRoleHandler(@RequestHeader("Authorization") String jwt, @RequestBody ChangeRoleRequest roleReq) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User updateRoleUser = userService.changeUserRole(roleReq);
        return new ResponseEntity<>(updateRoleUser, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> changePassword(@RequestHeader("Authorization") String jwt, @RequestBody ChangePasswordRequest req) throws UserException {
        userService.changeUserPassword(jwt, req);
        ApiResponse apiResponse = new ApiResponse("Password changed successfully" , true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<String> deleteUserAccount(@RequestHeader("Authorization") String jwt, @PathVariable String userId) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.deleteUserAccount(userId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }

}
