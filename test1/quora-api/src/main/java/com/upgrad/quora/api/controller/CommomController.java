package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserProfileBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommomController {
@Autowired
UserProfileBusinessService userProfileBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("userId") final String userUuid,
                                                       @RequestHeader("accessToken") final String accessToken) throws AuthorizationFailedException, UserNotFoundException {
        String [] bearerToken = accessToken.split("Bearer ");
        final UserEntity userEntity = userProfileBusinessService.getUser(userUuid, bearerToken[1]);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .emailAddress(userEntity.getEmail())
                .country(userEntity.getCountry())
                .aboutMe(userEntity.getAboutMe())
                .dob(userEntity.getDob())
                .contactNumber(userEntity.getContactNumber());
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

}
