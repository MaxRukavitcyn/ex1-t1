package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.AbstractAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AbstractAccount, Long> {

}
