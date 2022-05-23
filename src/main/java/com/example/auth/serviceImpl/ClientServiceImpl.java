package com.example.auth.serviceImpl;


import com.example.auth.exception.LoginException;
import com.example.auth.exception.RegistrationException;
import com.example.auth.model.repository.ClientEntity;
import com.example.auth.repository.ClientRepository;
import com.example.auth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public void register(String clientId, String clientSecret) {
        if(clientRepository.findById(clientId).isPresent()) {
            throw new RegistrationException(
                    "Client with id: " + clientId + " already registered");
        }
        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<ClientEntity> optionalClientEntity = clientRepository
                .findById(clientId);
        if(optionalClientEntity.isEmpty()) {
            throw new LoginException(
                    "Client with id: " + clientId + " not found");
        }
        ClientEntity clientEntity = optionalClientEntity.get();
        // тоже самое что и выше
//        ClientEntity clientEntity = clientRepository.findById(clientId)
//                .orElseThrow(() -> new LoginException(
//                "Client with id: " + clientId + " not found"));
        if(!BCrypt.checkpw(clientSecret, clientEntity.getHash())) {
            throw new LoginException("Secret is incorrect");
        }
    }
}
