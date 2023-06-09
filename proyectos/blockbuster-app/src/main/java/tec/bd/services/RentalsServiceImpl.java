package tec.bd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Rentals;
import tec.bd.repository.ClientRepository;
import tec.bd.repository.RentalsRepository;
import tec.bd.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class RentalsServiceImpl implements RentalsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RentalsServiceImpl.class);
    private final RentalsRepository rentalsRepository;

    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;

    public RentalsServiceImpl(RentalsRepository rentalsRepository, ClientRepository clientRepository, MovieRepository movieRepository) {
        requireNonNull(rentalsRepository);
        requireNonNull(clientRepository);
        requireNonNull(movieRepository);

        this.rentalsRepository = rentalsRepository;
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Rentals> getRentals() {
        return this.rentalsRepository.findAll();
    }

    @Override
    public Optional<Rentals> getRentalsById(int rentalsId) {
        // validacion
        if (rentalsId > 0) {
            return this.rentalsRepository.findById(rentalsId);
        }
        return Optional.empty();
    }

    @Override
    public Rentals newRentals(Rentals rentals) {
        requireNonNull(rentals);
        requireNonNull(rentals.getClient());
        requireNonNull(rentals.getMovie());

        if (rentals.getRentalDate() == null) {
            throw new RuntimeException("Release date is not valid");
        }
        if (rentals.getClient().getClientId() <= 0) {
            throw new RuntimeException("Client Id " + rentals.getClient().getClientId() + " is not valid");
        }
        if (rentals.getMovie().getMovieId() <= 0) {
            throw new RuntimeException("Movie Id " + rentals.getMovie().getMovieId() + " is not valid");
        }

        LOGGER.debug("Creating new Rentals via adhoc SQL commands");
        this.clientRepository.findById(rentals.getClient().getClientId()).orElseThrow();
        this.movieRepository.findById(rentals.getMovie().getMovieId()).orElseThrow();
        return this.rentalsRepository.save(rentals);
    }

    @Override
    public void removeRentals(int rentalsId) {
        if (rentalsId < 1) {
            LOGGER.error("Rentals id {} is invalid", rentalsId);
            throw new RuntimeException("Rentals id is invalid");
        }
        var rentalsInCatalog = this.rentalsRepository.findById(rentalsId);

        rentalsInCatalog.ifPresentOrElse((re) -> {
            this.rentalsRepository.delete(rentalsId);
        }, () -> {
            LOGGER.debug("Rentals id {} doesnt exist in catalog", rentalsId);
            new RuntimeException("Rentals doesnt exists in catalog");
        });
    }

    @Override
    public Rentals updateRentals(Rentals rentals) {
        requireNonNull(rentals);
        requireNonNull(rentals.getClient());
        requireNonNull(rentals.getMovie());

        if ((rentals.getRentalDate() == null) && (rentals.getClient().getClientId() <= 0) 
            && (rentals.getMovie().getMovieId() <= 0)) {
            throw new RuntimeException("There are not changes to make");
        }

        LOGGER.debug("Updating the Rentals via adhoc SQL commands");
        this.clientRepository.findById(rentals.getClient().getClientId()).orElseThrow();
        this.movieRepository.findById(rentals.getMovie().getMovieId()).orElseThrow();
        return this.rentalsRepository.update(rentals);
    }
}
