package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.*;
import com.health.sugar.lf10sugarhealth.model.Account;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.repository.AccountRepository;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(ProfileService.class);

    public List<Profile> getByAccountId(UUID accountId) throws EmptyResponseException, AccountNotFoundException {
        Account account = accountService.getByAccountId(accountId);

        List<Profile> profileList = account.getProfileList();

        if (profileList.isEmpty()) {
            throw new EmptyResponseException("Profile response is empty");
        }
        return profileList;
    }

    public Profile getPrimary(UUID accountId) throws AccountNotFoundException, EmptyResponseException {
        Account account = accountService.getByAccountId(accountId);

        List<Profile> profileList = account.getProfileList();

        for (Profile profile : profileList) {
            if (profile.getPrimaryProfile()) {
                return profile;
            }
        }
        throw new EmptyResponseException("No primary profile found");
    }

    public Profile create(UUID accountId, String name, String imageUrl, Boolean primary) throws AccountNotFoundException, ProfileLimitExceededWithMembershipException, ProfileLimitExceededNoMembershipException {

        Account account = accountService.getByAccountId(accountId);

        int allowedProfiles = 2;
        boolean isPremium = account.getMembershipStatus().getActive();

        if (isPremium) {
            allowedProfiles = 8;
        }
        int currentProfileCount = 0;

        logger.info(account.toString());
        if (!Objects.isNull(account.getProfileList())) {
            if (!account.getProfileList().isEmpty()) {
                currentProfileCount = account.getProfileList().size();
            }
        }

        if (currentProfileCount < allowedProfiles) {
            logger.info("currentProfileCount {} < allowedProfiles {}", currentProfileCount, allowedProfiles);
            return profileRepository.save(
                    new Profile(
                            name,
                            account,
                            imageUrl,
                            primary
                    )
            );
        } else {
            logger.info("currentProfileCount {} >= allowedProfiles {}", currentProfileCount, allowedProfiles);
            if (isPremium) {
                throw new ProfileLimitExceededWithMembershipException("Too many profiles");
            } else {
                throw new ProfileLimitExceededNoMembershipException("Too many profiles");

            }
        }
    }

    public Profile create(Account account, String imageUrl) {
        return profileRepository.save(
                new Profile(
                        account.getDisplayName(),
                        account,
                        imageUrl,
                        true
                )
        );
    }
}
