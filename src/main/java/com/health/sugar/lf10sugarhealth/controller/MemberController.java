package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.common.exceptions.MemberNotFoundException;
import com.health.sugar.lf10sugarhealth.common.exceptions.MembershipNotFoundException;
import com.health.sugar.lf10sugarhealth.model.Member;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.service.MemberService;
import com.health.sugar.lf10sugarhealth.service.MembershipStatusService;
import com.health.sugar.lf10sugarhealth.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    ProfileService profileService;

    @Autowired
    MembershipStatusService membershipStatusService;

    Logger logger = LoggerFactory.getLogger(MemberController.class);


    @GetMapping("/")
    public ResponseEntity<List<Member>> getAll() {
        try {
            List<Member> members = memberService.getAllMember();

            return new ResponseEntity<>(members, HttpStatus.OK);

        } catch (EmptyResponseException emptyResponseException) {
            logger.error(emptyResponseException.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable("id") UUID id) {
        try {
            Member member = memberService.getMemberById(id);

            return new ResponseEntity<>(member, HttpStatus.OK);

        } catch (MemberNotFoundException memberNotFoundException) {
            logger.error(memberNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/membership")
    public ResponseEntity<MembershipStatus> getMembershipById(@PathVariable("id") UUID id) {
        try {
            MembershipStatus membershipStatus = membershipStatusService.getMembershipStatusById(id);

            return new ResponseEntity<>(membershipStatus, HttpStatus.OK);
        } catch (MembershipNotFoundException membershipNotFoundException) {
            logger.error(membershipNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfileByMember(@PathVariable("id") UUID member_id) {
        try {
            List<Profile> profiles = profileService.getProfilesByMemberId(member_id);

            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (EmptyResponseException emptyResponseException) {
            logger.error(emptyResponseException.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> deleteById(@PathVariable("id") UUID member_id) {
        try {
            memberService.deleteMember(member_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Member> create(@RequestBody Member memberRequestBody) {
        try {
            Member member = memberService.createMember(memberRequestBody.getDisplayName(), memberRequestBody.getLogin_uid());

            return new ResponseEntity<>(member, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException dve) {
            logger.error("Ein Nutzer mit diesem Login-Konto existiert bereits.");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
