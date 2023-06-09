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

@Command(name = "loanu", description = "Update rating data in catalog ")
public class UpdateRentalsCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateRentalsCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<date>", description = "date of the rental")
    private Date rentalsDate;

    @Parameters(paramLabel = "<client id>", description = "The client id")
    private int rentalsClientId;

    @Parameters(paramLabel = "<movie id>", description = "The rental movie id")
    private int rentalsMovieId;

    @Override
    public Integer call() throws Exception {

        var rentals = new Rentals();
        var movie = new Movie();
        var client = new Client();

        movie.setMovieId(rentalsMovieId);
        client.setClientId(rentalsClientId);
        rentals.setRentalDate(rentalsDate);
        rentals.setClient(client);
        rentals.setMovie(movie);

        try {
            var updatedRentals = applicationContext.rentalsService.updateRentals(rentals);
            System.out.println("Rental Id: " + updatedRentals.getRentId());
            System.out.println("Created On: " + updatedRentals.getRentalDate());
            System.out.println("Cliend Id: " + updatedRentals.getClient().getClientId());
            System.out.println("Movie: " + updatedRentals.getMovie().getTitle());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
