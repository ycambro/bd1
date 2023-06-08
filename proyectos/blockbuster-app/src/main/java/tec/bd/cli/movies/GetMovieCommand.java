package tec.bd.cli.movies;

import picocli.CommandLine;
import tec.bd.ApplicationContext;
import tec.bd.entities.Movie;

@CommandLine.Command(name = "movr", description = "Get movie in catalog by id")
public class GetMovieCommand implements Runnable {

    private static ApplicationContext applicationContext = ApplicationContext.init();
    @CommandLine.Parameters(paramLabel = "<movie id>", defaultValue = "0", description = "The movie id")
    private int movieId;

    @Override
    public void run() {
        if (movieId == 0) {
            var movies = applicationContext.movieService.getMovies();

            System.out.println("Movie Catalog");
            System.out.println("Id\t Title\t Release Date\t Category\t Units Available");
            for (Movie m : movies) {
                System.out.println(m.getMovieId() + "\t " + m.getTitle() + "\t " + m.getReleaseDateOnly() + "\t "
                        + m.getCategory().getCategoryName() + "\t " + m.getUnitsAvailable());
            }
        } else {
            applicationContext.movieService.getMovieById(movieId).ifPresentOrElse((movie) -> {
                System.out.println("Movie Id: " + movie.getMovieId());
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Release Date: " + movie.getReleaseDateOnly());
                System.out.println("Category: " + movie.getCategory().getCategoryName());
                System.out.println("Units Available: " + movie.getUnitsAvailable());
            }, () -> System.out.println("Movie id " + movieId + " not found"));
        }
    }
}
