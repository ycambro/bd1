package tec.bd.services;

import tec.bd.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getClients();

    Optional<Client> getClientById(int clientId);

    Client newClient(Client client);

    void removeClient(int clientId);

    Client updateClient(Client client);

}
