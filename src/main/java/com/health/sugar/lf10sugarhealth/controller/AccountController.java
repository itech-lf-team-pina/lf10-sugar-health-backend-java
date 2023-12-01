package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.common.exceptions.AccountNotFoundException;
import com.health.sugar.lf10sugarhealth.dto.request.CreateAccountDTO;
import com.health.sugar.lf10sugarhealth.dto.response.AccountCreationResponseDTO;
import com.health.sugar.lf10sugarhealth.model.Account;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
import com.health.sugar.lf10sugarhealth.service.AccountService;
import com.health.sugar.lf10sugarhealth.service.ProfileService;
import com.health.sugar.lf10sugarhealth.service.SugarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ProfileService profileService;

    @Autowired
    SugarService sugarService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);


    @GetMapping("/")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAll();

            return new ResponseEntity<>(accounts, HttpStatus.OK);

        } catch (EmptyResponseException emptyResponseException) {
            logger.error(emptyResponseException.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") UUID id) {
        try {
            Account account = accountService.getByAccountId(id);

            return new ResponseEntity<>(account, HttpStatus.OK);

        } catch (AccountNotFoundException accountNotFoundException) {
            logger.error(accountNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/membership")
    public ResponseEntity<MembershipStatus> getMembershipById(@PathVariable("id") UUID id) {
        try {
            Account account = accountService.getByAccountId(id);

            return new ResponseEntity<>(account.getMembershipStatus(), HttpStatus.OK);
        } catch (AccountNotFoundException accountNotFoundException) {
            logger.error(accountNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfileByAccount(@PathVariable("id") UUID account_id) {
        try {
            List<Profile> profiles = profileService.getByAccountId(account_id);

            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (EmptyResponseException emptyResponseException) {
            logger.error(emptyResponseException.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/sugar")
    public ResponseEntity<List<SugarInput>> getSugarIntakeByAccount(@PathVariable("id") UUID account_id) {
        try {
            List<SugarInput> sugarInputList = sugarService.getSugarByAccount(account_id);

            return new ResponseEntity<>(sugarInputList, HttpStatus.OK);
        } catch (EmptyResponseException emptyResponseException) {
            logger.error(emptyResponseException.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccountById(@PathVariable("id") UUID account_id) {
        try {
            accountService.delete(account_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<AccountCreationResponseDTO> createAccount(@RequestBody CreateAccountDTO accountRequestBody) {
        try {
            Boolean accountExists = accountService.isAccountAlreadyCreated(accountRequestBody.getUid());

            if (!accountExists) {
                Account account = accountService.create(
                        accountRequestBody.getName(),
                        accountRequestBody.getUid()
                );

                Profile profile = profileService.create(
                        account,
                        accountRequestBody.getImageUrl()
                );

                return new ResponseEntity<>(new AccountCreationResponseDTO(account, profile), HttpStatus.CREATED);
            } else {
                Account account = accountService.getByLoginId(accountRequestBody.getUid());
                Profile profile = profileService.getPrimary(account.getId());
                return new ResponseEntity<>(new AccountCreationResponseDTO(account, profile), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
