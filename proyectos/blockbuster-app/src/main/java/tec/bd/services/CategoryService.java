package tec.bd.services;

import tec.bd.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(int categoryId);

    Category newCategory(Category category);

    void removeCategory(int categoryId);

    Category updateCategory(Category category);

}
