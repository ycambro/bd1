package tec.bd.cli.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Category;
import tec.bd.entities.Movie;

import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "movu", description = "Update movie data in catalog ")
public class UpdateMovieCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateMovieCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @CommandLine.Parameters(paramLabel = "<movie id>", description = "The movie id")
    private int movieId;

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
        var category = new Category();

        applicationContext.categoryService.getCategoryById(movieCategoryId).ifPresentOrElse((cat) -> {
            category.setCategoryId(cat.getCategoryId());
            category.setCategoryName(cat.getCategoryName());
            category.setDescription(cat.getDescription());
        }, () -> System.out.println("Category id " + movieCategoryId + " not found"));
        movie.setCategory(category);
        movie.setMovieId(movieId);
        movie.setReleaseDate(movieReleaseDate);
        movie.setTitle(movieTitle);
        movie.setUnitsAvailable(movieUnitsAvailable);

        try {
            var updatedMovie = applicationContext.movieService.updateMovie(movie);

            System.out.println("Movie Id: " + updatedMovie.getMovieId());
            System.out.println("Title: " + updatedMovie.getTitle());
            System.out.println("Release Date: " + updatedMovie.getReleaseDateOnly());
            System.out.println("Category Id: " + updatedMovie.getCategory().getCategoryId());
            System.out.println("Units Available: " + updatedMovie.getUnitsAvailable());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
