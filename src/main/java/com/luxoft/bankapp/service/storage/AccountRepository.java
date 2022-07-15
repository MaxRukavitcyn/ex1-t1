package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.AbstractAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AccountRepository extends JpaRepository<AbstractAccount, Long> {
    Optional<AbstractAccount> getById(Long id);

    @Query("select a from AbstractAccount a where a.id = :id")
    CompletableFuture<AbstractAccount> getAccount(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update AbstractAccount a set a.balance = :balance where  a.id = :id")
    void updateResetBalance(@Param("id") Long id, @Param("balance") double balance);
}
