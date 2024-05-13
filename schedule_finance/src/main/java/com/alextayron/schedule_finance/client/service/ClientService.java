package com.alextayron.schedule_finance.client.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alextayron.schedule_finance.client.repository.ClientRepository;
import com.alextayron.schedule_finance.client.controller.CreateClientDto;
import com.alextayron.schedule_finance.client.controller.UpdateClientDto;
import com.alextayron.schedule_finance.client.entity.Client;;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public UUID createClient(CreateClientDto createClientDto) {
        var entity = new Client(
                UUID.randomUUID(),
                createClientDto.name(),
                createClientDto.phone(),
                Instant.now(),
                null);

        var clientSaved = clientRepository.save(entity);

        return clientSaved.getClientId();
    }

    public Optional<Client> getClientById(String clientId) {

        return clientRepository.findById(UUID.fromString(clientId));

    }

    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    public void updateClientById(String clientId, UpdateClientDto updateClientDto) {
        var id = UUID.fromString(clientId);
        var clientEntity = clientRepository.findById(id);

        if (clientEntity.isPresent()) {
            var client = clientEntity.get();

            if (updateClientDto.name() != null) {
                client.setClientname(updateClientDto.name());
            }
            if (updateClientDto.phone() != null) {
                client.setPhone(updateClientDto.phone());
            }

            clientRepository.save(client);
        }
    }

    public void deleteById(String clientId) {
        var id = UUID.fromString(clientId);
        var clientExistent = clientRepository.existsById(id);

        if (clientExistent) {
            clientRepository.deleteById(id);
        }
    }
}
