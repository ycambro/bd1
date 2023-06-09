package tec.bd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Client;
import tec.bd.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ClientServiceImpl implements ClientService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        requireNonNull(clientRepository);

        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(int clientId) {
        // validacion
        if (clientId > 0) {
            return this.clientRepository.findById(clientId);
        }
        return Optional.empty();
    }

    @Override
    public Client newClient(Client client) {
        requireNonNull(client);

        if (client.getName() == null || client.getName().isEmpty()
                || client.getName().isBlank()) {
            throw new RuntimeException("Client name is null or empty");
        }
        if (client.getLastname() == null || client.getLastname().isEmpty()
                || client.getLastname().isBlank()) {
            throw new RuntimeException("Client lastname is null or empty");
        }
        if (client.getEmail() == null || client.getEmail().isEmpty()
                || client.getEmail().isBlank()) {
            throw new RuntimeException("Client email is null or empty");
        }
        if (client.getPhoneNumber() == null || client.getEmail().isEmpty()
                || client.getEmail().isBlank()) {
            throw new RuntimeException("Client phone number is null or empty");
        }

        LOGGER.debug("Creating new client via adhoc SQL commands");
        return this.clientRepository.save(client);
    }

    @Override
    public void removeClient(int clientId) {
        if (clientId < 1) {
            LOGGER.error("Client id {} is invalid", clientId);
            throw new RuntimeException("Client id is invalid");
        }

        var clientInCatalog = this.clientRepository.findById(clientId);

        clientInCatalog.ifPresentOrElse((cl) -> {
            this.clientRepository.delete(clientId);
        }, () -> {
            LOGGER.debug("Client id {} doesn't exist in catalog", clientId);
            new RuntimeException("Client doesn't exists in catalog");
        });
    }

    @Override
    public Client updateClient(Client client) {
        requireNonNull(client);

        if ((client.getName() == null || client.getName().isEmpty()|| client.getName().isBlank())
                && (client.getLastname() == null || client.getLastname().isEmpty()|| client.getLastname().isBlank())
                && (client.getEmail() == null || client.getEmail().isEmpty() || client.getEmail().isBlank())
                && (client.getPhoneNumber() == null || client.getEmail().isEmpty()|| client.getEmail().isBlank())) {
            throw new RuntimeException("There are not changes to make");
        }

        LOGGER.debug("Updating the Client via adhoc SQL commands");
        return this.clientRepository.update(client);
    }
}
