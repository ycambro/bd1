package tec.bd.repository;

import java.util.List;
import java.util.Optional;

import tec.bd.entities.Category;

public interface CategoryRepository extends CRUDRepository<Category, Integer> {
    public List<Category> findAll();

    public Optional<Category> findById(Integer entityId);

    public Category save (Category entity);

    public void delete(Integer entityId);

    public Category update(Category entity);
}
