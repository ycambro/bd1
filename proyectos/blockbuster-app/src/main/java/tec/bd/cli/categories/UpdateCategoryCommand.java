package tec.bd.cli.categories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Category;

import java.util.concurrent.Callable;

@Command(name = "catu", description = "Update category data in catalog ")
public class UpdateCategoryCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateCategoryCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<category name>", description = "The category name")
    private String categoryName;

    @Parameters(paramLabel = "<description>", defaultValue = "No desciption", description = "The category description")
    private String categoryDescription;

    @Override
    public Integer call() throws Exception {

        var category = new Category();

        try {
            var updatedCategory = applicationContext.categoryService.updateCategory(category);

            System.out.println("Category Id: " + updatedCategory.getCategoryId());
            System.out.println("Category Name: " + updatedCategory.getCategoryName());
            System.out.println("Description: " + updatedCategory.getDescription());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
