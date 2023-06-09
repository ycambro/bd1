package tec.bd.cli.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Rentals;
import tec.bd.entities.Review;

import java.util.concurrent.Callable;

@Command(name = "movd", description = "Delete movie in catalog ")
public class DeleteMovieCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteMovieCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<movie id>", description = "The movie id to be deleted")
    private int movieId;

    @Override
    public Integer call() throws Exception {
        try {
            var rentals = applicationContext.rentalsService.getRentals();
            for (Rentals re : rentals) {
                if (re.getMovie().getMovieId() == movieId) {
                    throw new RuntimeException("This movie is rental");
                }
            }

            var reviews = applicationContext.reviewService.getReview();
            for (Review rev : reviews) {
                if (rev.getMovie().getMovieId() == movieId) {
                    throw new RuntimeException("This movie has a review");
                }
            }
            applicationContext.movieService.removeMovie(movieId);
            System.out.println("The movie " + movieId + " was deleted sucessfully.");
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
