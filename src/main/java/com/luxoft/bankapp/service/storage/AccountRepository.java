package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.AbstractAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AbstractAccount, Long> {

}
