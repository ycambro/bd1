package tec.bd.cli.categories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Movie;

import java.util.concurrent.Callable;

@Command(name = "catd", description = "Delete category in catalog ")
public class DeleteCategoryCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteCategoryCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<category id>", description = "The category id to be deleted")
    private int categoryId;

    @Override
    public Integer call() throws Exception {
        try {
            var movies = applicationContext.movieService.getMovies();
            for (Movie mov : movies) {
                if (mov.getCategory().getCategoryId() == categoryId) {
                    throw new RuntimeException("This category have a movie associated");
                }
            }
            applicationContext.categoryService.removeCategory(categoryId);
            System.out.println("The category " + categoryId + " was deleted sucessfully.");
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
