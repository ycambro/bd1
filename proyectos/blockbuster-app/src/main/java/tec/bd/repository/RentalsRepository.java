package tec.bd.repository;

import java.util.List;
import java.util.Optional;

import tec.bd.entities.Rentals;

public interface RentalsRepository extends CRUDRepository<Rentals, Integer> {
    public List<Rentals> findAll();

    public Optional<Rentals> findById(Integer entityId);

    public Rentals save (Rentals entity);

    public void delete(Integer entityId);

    public Rentals update(Rentals entity);
}
