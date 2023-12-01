package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.common.exceptions.ProfileLimitExceededNoMembershipException;
import com.health.sugar.lf10sugarhealth.common.exceptions.ProfileLimitExceededWithMembershipException;
import com.health.sugar.lf10sugarhealth.dto.request.CreateProfileRequestBody;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.repository.AccountRepository;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import com.health.sugar.lf10sugarhealth.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    Logger logger = LoggerFactory.getLogger(ProfileController.class);


    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        try {
            List<Profile> profiles = new ArrayList<>(profileRepository.findAll());

            if (profiles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable("id") UUID id) {
        try {
            Optional<Profile> profileOptional = profileRepository.findById(id);

            return profileOptional
                    .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profile> deleteProfileById(@PathVariable("id") UUID id) {
        try {
            profileRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(@RequestBody CreateProfileRequestBody requestBody) {
        try {
            Profile profile = profileService.create(requestBody.getAccountID(), requestBody.getName(), requestBody.getImageUrl(), false);

            if (Objects.isNull(profile)) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(profile, HttpStatus.CREATED);
            }
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (ProfileLimitExceededNoMembershipException | ProfileLimitExceededWithMembershipException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
