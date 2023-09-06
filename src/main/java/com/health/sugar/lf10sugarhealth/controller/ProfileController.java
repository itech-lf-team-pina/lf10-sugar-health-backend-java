package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.dto.CreateProfileRequestBody;
import com.health.sugar.lf10sugarhealth.model.Member;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.repository.MemberRepository;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
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
    MemberRepository memberRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    Logger logger = LoggerFactory.getLogger(ProfileController.class);


    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAll() {
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
    public ResponseEntity<Profile> getById(@PathVariable("id") UUID id) {
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
    public ResponseEntity<Profile> deleteById(@PathVariable("id") UUID id) {
        try {
            profileRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Profile> create(@RequestBody CreateProfileRequestBody requestBody) {
        try {
            logger.info(requestBody.toString());
            Member member = memberRepository.findById(requestBody.getMemberID()).get();
            Profile profile = null;


            int allowedProfiles = 2;
            boolean isPremium = member.getMembershipStatus().getActive();

            if (isPremium) {
                allowedProfiles = 8;
            }

            int currentProfileCount = profileRepository.countProfileByMemberId(requestBody.getMemberID());

            if (currentProfileCount < allowedProfiles) {
                logger.info("currentProfileCount {} < allowedProfiles {}", currentProfileCount, allowedProfiles);
                profile = profileRepository.save(
                        new Profile(requestBody.getName(), member, requestBody.getImageUrl()));
            } else {
                logger.info("currentProfileCount {} >= allowedProfiles {}", currentProfileCount, allowedProfiles);
            }

            if (Objects.isNull(profile)) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(profile, HttpStatus.CREATED);
            }
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
