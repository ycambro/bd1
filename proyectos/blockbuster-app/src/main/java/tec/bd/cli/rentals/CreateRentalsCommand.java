package tec.bd.cli.rentals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Client;
import tec.bd.entities.Movie;
import tec.bd.entities.Rentals;

import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "loanc", description = "Create new rental in catalog ")
public class CreateRentalsCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreateRentalsCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<date>", description = "date of the rental")
    private Date rentalsDate;

    @Parameters(paramLabel = "<client id>", description = "The client id")
    private int rentalsClientId;

    @Parameters(paramLabel = "<movie id>", description = "The rental movie id")
    private int rentalsMovieId;


    @Override
    public Integer call() throws Exception {

        var rental = new Rentals();
        var movie = new Movie();
        var client = new Client();
        applicationContext.movieService.getMovieById(rentalsMovieId).ifPresentOrElse((mov) -> {
            movie.setMovieId(mov.getMovieId());
            movie.setCategory(mov.getCategory());
            movie.setReleaseDate(mov.getReleaseDate());
            movie.setTitle(mov.getTitle());
            movie.setUnitsAvailable(mov.getUnitsAvailable());
        }, () -> System.out.println("Movie id " + rentalsMovieId + " not found"));

        applicationContext.clientService.getClientById(rentalsClientId).ifPresentOrElse((clie) -> {
            client.setClientId(clie.getClientId());
            client.setEmail(clie.getEmail());
            client.setLastname(clie.getLastname());
            client.setName(clie.getName());
            client.setPhoneNumber(clie.getPhoneNumber());
        }, () -> System.out.println("Client id " + rentalsClientId + " not found"));
        rental.setRentalDate(rentalsDate);
        rental.setClient(client);
        rental.setMovie(movie);

        try {
            var newRentals = applicationContext.rentalsService.newRentals(rental);
            System.out.println("Rental Id: " + newRentals.getRentId());
            System.out.println("Created On: " + newRentals.getRentalDate());
            System.out.println("Cliend Id: " + newRentals.getClient().getClientId());
            System.out.println("Movie: " + newRentals.getMovie().getTitle());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
