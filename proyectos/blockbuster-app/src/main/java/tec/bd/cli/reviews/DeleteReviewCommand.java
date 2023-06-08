package tec.bd.cli.reviews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;

import java.util.concurrent.Callable;

@Command(name = "revd", description = "Delete review in catalog ")
public class DeleteReviewCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteReviewCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<review id>", description = "The review id to be deleted")
    private int reviewId;

    @Override
    public Integer call() throws Exception {
        try {
            applicationContext.reviewService.removeReview(reviewId);
            System.out.println("The review " + reviewId + " was deleted sucessfully.");
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
