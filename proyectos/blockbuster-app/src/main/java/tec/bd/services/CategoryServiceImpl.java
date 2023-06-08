package tec.bd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Category;
import tec.bd.repository.CategoryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class CategoryServiceImpl implements CategoryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        requireNonNull(categoryRepository);

        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        // validacion
        if (categoryId > 0) {
            return this.categoryRepository.findById(categoryId);
        }
        return Optional.empty();
    }

    @Override
    public Category newCategory(Category category) {
        requireNonNull(category);

        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()
                || category.getCategoryName().isBlank()) {
            throw new RuntimeException("Category name is null or empty");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()
                || category.getDescription().isBlank()) {
            throw new RuntimeException("Category description is null or empty");
        }

        LOGGER.debug("Creating new category via adhoc SQL commands");
        return this.categoryRepository.save(category);
    }

    @Override
    public void removeCategory(int categoryId) {
        if (categoryId < 1) {
            LOGGER.error("Category id {} is invalid", categoryId);
            throw new RuntimeException("Category id is invalid");
        }

        var categoryInCatalog = this.categoryRepository.findById(categoryId);

        categoryInCatalog.ifPresentOrElse((c) -> {
            this.categoryRepository.delete(categoryId);
        }, () -> {
            LOGGER.debug("Category id {} doesn't exist in catalog", categoryId);
            new RuntimeException("Category doesn't exists in catalog");
        });
    }

    @Override
    public Category updateCategory(Category category) {
        requireNonNull(category);

        if ((category.getCategoryName() == null || category.getCategoryName().isEmpty()
                || category.getCategoryName().isBlank())
                && (category.getDescription() == null || category.getDescription().isEmpty()
                        || category.getDescription().isBlank())) {
            throw new RuntimeException("There are not changes to make");
        }

        LOGGER.debug("Updating the category via adhoc SQL commands");
        return this.categoryRepository.update(category);
    }
}
