package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long>  {

    Optional<Client> findByName(String name);
}
