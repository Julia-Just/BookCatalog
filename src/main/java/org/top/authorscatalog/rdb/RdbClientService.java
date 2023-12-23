package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Client;
import org.top.authorscatalog.rdb.repository.ClientRepository;
import org.top.authorscatalog.service.ClientService;

import java.util.Optional;

// RdbClientService - имплементация ClientService, работающая с СУБД
@Service
public class RdbClientService implements ClientService {

    // репозиторий для выполнения операций
    private final ClientRepository clientRepository;

    public RdbClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> save(Client client) {
        return Optional.of(clientRepository.save(client));
    }

    @Override
    public Optional<Client> deleteById(Integer id) {
        Optional<Client> deleted = findById(id);
        if (deleted.isPresent()) {
            clientRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Client> update(Client client) {
        Optional<Client> updated = findById(client.getId());
        if (updated.isPresent()) {
            updated = Optional.of(clientRepository.save(client));
        }
        return updated;
    }
}
