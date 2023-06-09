package tec.bd.cli.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Movie;

import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "movc", description = "Create new movie in catalog ")
public class CreateMovieCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreateMovieCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<title>", description = "The movie title")
    private String movieTitle;

    @Parameters(paramLabel = "<release date>", description = "Release date")
    private Date movieReleaseDate;

    @Parameters(paramLabel = "<category id>", description = "The movie category id")
    private int movieCategoryId;

    @Parameters(paramLabel = "<units_available>", description = "The quantity of units for the movie")
    private int movieUnitsAvailable;

    @Override
    public Integer call() throws Exception {
        var movie = new Movie();
        applicationContext.categoryService.getCategoryById(movieCategoryId).ifPresentOrElse((category) -> {
            movie.setCategory(category);
        }, () -> System.out.println("Category id " + movieCategoryId + " not found"));
        movie.setReleaseDate(movieReleaseDate);
        movie.setTitle(movieTitle);
        movie.setUnitsAvailable(movieUnitsAvailable);

        try {
            var newMovie = applicationContext.movieService.newMovie(movie);

            System.out.println("Movie Id: " + newMovie.getMovieId());
            System.out.println("Title: " + newMovie.getTitle());
            System.out.println("Release Date: " + newMovie.getReleaseDateOnly());
            System.out.println("Category: " + newMovie.getCategory().getCategoryName());
            System.out.println("Units: " + newMovie.getUnitsAvailable());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
