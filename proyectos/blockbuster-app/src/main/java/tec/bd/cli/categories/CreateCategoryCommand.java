package tec.bd.cli.categories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Category;

import java.util.concurrent.Callable;

@Command(name = "catc", description = "Create new category in catalog ")
public class CreateCategoryCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreateCategoryCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<category name>", description = "The category name")
    private String categoryName;

    @Parameters(paramLabel = "<description>",defaultValue = "No description", description = "The description of the category")
    private String description;

    @Override
    public Integer call() throws Exception {

        var category = new Category();

        try {
            var newCategory = applicationContext.categoryService.newCategory(category);

            System.out.println("Category Id: " + newCategory.getCategoryId());
            System.out.println("Category Name: " + newCategory.getCategoryName());
            System.out.println("Description: " + newCategory.getDescription());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
