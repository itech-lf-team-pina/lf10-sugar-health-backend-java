package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.model.Member;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.repository.MemberRepository;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    @Autowired
    ProfileRepository profileRepository;

    Logger logger = LoggerFactory.getLogger(MemberController.class);


    @GetMapping("/")
    public ResponseEntity<List<Member>> getAll() {
        try {
            List<Member> members = new ArrayList<>(memberRepository.findAll());

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable("id") UUID id) {
        try {
            Optional<Member> memberOptional = memberRepository.findById(id);

            return memberOptional
                    .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/membership")
    public ResponseEntity<MembershipStatus> getMembershipById(@PathVariable("id") UUID id) {
        try {
            Optional<MembershipStatus> membershipStatusOptional = membershipStatusRepository.findByMemberId(id);

            return membershipStatusOptional
                    .map(membershipStatus -> new ResponseEntity<>(membershipStatus, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfileByMember(@PathVariable("id") UUID id) {
        try {
            List<Profile> profiles = profileRepository.findAllByMemberId(id);

            if (profiles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> deleteById(@PathVariable("id") UUID id) {
        try {
            memberRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Member> create(@RequestBody Member memberRequestBody) {
        try {
            MembershipStatus membershipStatus = membershipStatusRepository.save(new MembershipStatus());

            Member member = memberRepository.save(
                    new Member(memberRequestBody.getDisplayName(), membershipStatus, memberRequestBody.getLogin_uid()));

            membershipStatus.setMember(member);

            membershipStatusRepository.save(membershipStatus);

            return new ResponseEntity<>(member, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException dve)
        {
            logger.error("Ein Nutzer mit diesem Login-Konto existiert bereits.");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
