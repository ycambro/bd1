package tec.bd.cli.categories;

import picocli.CommandLine;
import tec.bd.ApplicationContext;
import tec.bd.entities.Category;

@CommandLine.Command(name = "catr", description = "Get category in catalog by id")
public class GetCategoryCommand implements Runnable {

    private static ApplicationContext applicationContext = ApplicationContext.init();
    @CommandLine.Parameters(paramLabel = "<category id>", defaultValue = "0", description = "The category id")
    private int categoryId;

    @Override
    public void run() {
        if (categoryId == 0) {
            var categories = applicationContext.categoryService.getCategories();

            System.out.println("Category Catalog");
            System.out.println("Id\t Category Name\t Description");
            for (Category c : categories) {
                System.out.println(c.getCategoryId() + "\t " + c.getCategoryName() + "\t " + c.getDescription());
            }
        } else {
            applicationContext.categoryService.getCategoryById(categoryId).ifPresentOrElse((category) -> {
                System.out.println("Category Id: " + category.getCategoryId());
                System.out.println("Category name: " + category.getCategoryName());
                System.out.println("Description: " + category.getDescription());
            }, () -> System.out.println("Category id " + categoryId + " not found"));
        }
    }
}
