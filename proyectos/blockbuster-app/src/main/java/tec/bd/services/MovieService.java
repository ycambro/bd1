package tec.bd.services;

import tec.bd.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getMovies();

    Optional<Movie> getMovieById(int movieId);

    Movie newMovie(Movie movie);

    void removeMovie(int movieId);

    Movie updateMovie(Movie movie);

}
