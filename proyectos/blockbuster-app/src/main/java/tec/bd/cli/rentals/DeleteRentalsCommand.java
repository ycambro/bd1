package tec.bd.cli.rentals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;

import java.util.concurrent.Callable;

@Command(name = "loand", description = "Delete rental in catalog ")
public class DeleteRentalsCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteRentalsCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<rental id>", description = "The rental id to be deleted")
    private int rentalId;

    @Override
    public Integer call() throws Exception {
        try {
            applicationContext.rentalsService.removeRentals(rentalId);
            System.out.println("The rental " + rentalId + " was deleted sucessfully.");
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
