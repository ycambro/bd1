package tec.bd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.BlockbusterLog;
import tec.bd.repository.BlockbusterLogRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class BlockbusterLogServiceImpl implements BlockbusterLogService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlockbusterLogServiceImpl.class);
    private final BlockbusterLogRepository blockbusterLogRepository;

    public BlockbusterLogServiceImpl(BlockbusterLogRepository blockbusterLogRepository) {
        requireNonNull(blockbusterLogRepository);

        this.blockbusterLogRepository = blockbusterLogRepository;
    }

    @Override
    public List<BlockbusterLog> getEntry(int amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount log can not be less than 1");
        } else {
            LOGGER.debug("Showing the last entries in the log via adhoc SQL commands");
            return this.blockbusterLogRepository.findEntry(amount);
        }
    }

    @Override
    public List<BlockbusterLog> getCategories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategories'");
    }

    @Override
    public Optional<BlockbusterLog> getCategoryById(int categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategoryById'");
    }

    @Override
    public BlockbusterLog newCategory(BlockbusterLog category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newCategory'");
    }

    @Override
    public void removeCategory(int categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCategory'");
    }

    @Override
    public BlockbusterLog updateCategory(BlockbusterLog category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCategory'");
    }
}
