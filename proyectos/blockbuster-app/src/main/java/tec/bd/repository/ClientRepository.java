package tec.bd.repository;

import java.util.List;
import java.util.Optional;

import tec.bd.entities.Client;

public interface ClientRepository extends CRUDRepository<Client, Integer> {
    public List<Client> findAll();

    public Optional<Client> findById(Integer entityId);

    public Client save (Client entity);

    public void delete(Integer entityId);

    public Client update(Client entity);
}
