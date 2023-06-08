package tec.bd.services;

import tec.bd.entities.BlockbusterLog;

import java.util.List;
import java.util.Optional;

public interface BlockbusterLogService {

     public List<BlockbusterLog> getEntry(int amount);

    List<BlockbusterLog> getCategories();

    Optional<BlockbusterLog> getCategoryById(int categoryId);

    BlockbusterLog newCategory(BlockbusterLog category);

    void removeCategory(int categoryId);

    BlockbusterLog updateCategory(BlockbusterLog category);

}
