package tec.bd.cli.reviews;

import picocli.CommandLine;
import tec.bd.ApplicationContext;
import tec.bd.entities.Review;

@CommandLine.Command(name = "revr", description = "Get review in catalog by id")
public class GetReviewCommand implements Runnable {

    private static ApplicationContext applicationContext = ApplicationContext.init();
    @CommandLine.Parameters(paramLabel = "<review id>", defaultValue = "0", description = "The review id")
    private int reviewId;

    @Override
    public void run() {
        if (reviewId == 0) {
            var reviews = applicationContext.reviewService.getReview();

            System.out.println("Reviews Catalog");
            System.out.println("Id\t Rating\t Review Text\t Created On\t Client Id\t Movie");
            for (Review r : reviews) {
                System.out.println(r.getReviewId() + "\t " + r.getRating() + "\t " + r.getReviewText() + "\t " + r.getCreatedOn()
                        + "\t " + r.getClient().getClientId() + "\t " + r.getMovie().getTitle());
            }
        } else {
            applicationContext.reviewService.getReviewById(reviewId).ifPresentOrElse((review) -> {
                System.out.println("Review Id: " + review.getReviewId());
                System.out.println("Rating: " + review.getRating());
                System.out.println("Review text: " + review.getReviewText());
                System.out.println("Created On: " + review.getCreatedOn());
                System.out.println("Cliend Id: " + review.getClient().getClientId());
                System.out.println("Movie: " + review.getMovie().getTitle());
            }, () -> System.out.println("Movie id " + reviewId + " not found"));
        }
    }
}
