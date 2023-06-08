package tec.bd.services;

import tec.bd.entities.Rentals;

import java.util.List;
import java.util.Optional;

public interface RentalsService {

    List<Rentals> getRentals();

    Optional<Rentals> getRentalsById(int rentalsId);

    Rentals newRentals(Rentals rentals);

    void removeRentals(int rentalsId);

    Rentals updateRentals(Rentals rentals);

}
