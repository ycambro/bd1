package tec.bd.cli.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Rentals;
import tec.bd.entities.Review;

import java.util.concurrent.Callable;

@Command(name = "clid", description = "Delete client in catalog ")
public class DeleteClientCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteClientCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<client id>", description = "The client id to be deleted")
    private int clientId;

    @Override
    public Integer call() throws Exception {
        try {
            var rentals = applicationContext.rentalsService.getRentals();
            for (Rentals re : rentals) {
                if (re.getClient().getClientId() == clientId) {
                    throw new RuntimeException("This client has a movie");
                }
            }

            var reviews = applicationContext.reviewService.getReview();
            for (Review rev : reviews) {
                if (rev.getClient().getClientId() == clientId) {
                    throw new RuntimeException("This client has a review");
                }
            }
            applicationContext.clientService.removeClient(clientId);
            System.out.println("The client " + clientId + " was deleted sucessfully.");
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
