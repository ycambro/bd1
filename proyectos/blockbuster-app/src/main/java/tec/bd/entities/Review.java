package tec.bd.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/YYYY");

    private int reviewId;
    private int rating;
    private String reviewText;
    private Date createdOn;
    private Movie movie;
    private Client client;

    public Review() {

    }

    public Review(int reviewId, int rating, String reviewText, Date createdOn, Movie movie, Client client) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdOn = createdOn;
        this.movie = movie;
        this.client = client;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCreatedOnOnly() {
        return DATE_FORMATTER.format(this.createdOn);
    }
}
