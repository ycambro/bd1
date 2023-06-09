package tec.bd.cli.reviews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Client;
import tec.bd.entities.Movie;
import tec.bd.entities.Review;

import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "revu", description = "Update rating data in catalog ")
public class UpdateReviewCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateReviewCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<rating>", description = "The rating for the movie")
    private int reviewRating;

    @Parameters(paramLabel = "<review text>", description = "The review comment")
    private String reviewText;

    @Parameters(paramLabel = "<date>", description = "date of the review")
    private Date reviewCreatedOn;

    @Parameters(paramLabel = "<client id>", description = "The client id")
    private int reviewClientId;

    @Parameters(paramLabel = "<movie id>", description = "The review movie id")
    private int reviewMovieId;

    @Override
    public Integer call() throws Exception {

        var review = new Review();
        var movie = new Movie();
        var client = new Client();

        movie.setMovieId(reviewMovieId);
        client.setClientId(reviewClientId);
        review.setCreatedOn(reviewCreatedOn);
        review.setRating(reviewRating);
        review.setReviewText(reviewText);
        review.setClient(client);
        review.setMovie(movie);

        try {
            var updatedReview = applicationContext.reviewService.updateReview(review);

            System.out.println("Review Id: " + updatedReview.getReviewId());
            System.out.println("Rating: " + updatedReview.getRating());
            System.out.println("Review text: " + updatedReview.getReviewText());
            System.out.println("Created On: " + updatedReview.getCreatedOn());
            System.out.println("Cliend Id: " + updatedReview.getClient().getClientId());
            System.out.println("Movie: " + updatedReview.getMovie().getTitle());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
