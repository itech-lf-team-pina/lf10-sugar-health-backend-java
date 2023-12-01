package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = "SELECT m from Account m WHERE m.login_uid = :uid")
    Optional<Account> findAccountByLoginUid(String uid);
}
