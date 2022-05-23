package com.example.auth.repository;

import com.example.auth.model.repository.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {

}
