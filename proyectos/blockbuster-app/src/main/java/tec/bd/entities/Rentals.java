package tec.bd.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rentals {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/YYYY");

    private int rentId;
    private Date rentalDate;
    private Movie movie;
    private Client client;

    public Rentals() {

    }

    public Rentals(int rentId, Date rentalDate, Movie movie, Client client) {
        this.rentId = rentId;
        this.rentalDate = rentalDate;
        this.movie = movie;
        this.client = client;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
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

    public String getRentalDateOnly() {
        return DATE_FORMATTER.format(this.rentalDate);
    }
}
