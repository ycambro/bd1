package tec.bd.cli.rentals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Rentals;
import tec.bd.entities.Client;
import tec.bd.entities.Movie;

import java.util.Date;
import java.util.UUID;
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

        var client = new Client();
        var movie = new Movie();
        var rental = new Rentals();

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
