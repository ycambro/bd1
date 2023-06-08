package tec.bd.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tec.bd.entities.BlockbusterLog;
import tec.bd.entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static tec.bd.repository.Queries.*;

public class BlockbusterLogRepositoryImpl extends BaseRepository<BlockbusterLog, Integer> implements BlockbusterLogRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockbusterLogRepositoryImpl.class);

    public BlockbusterLogRepositoryImpl(HikariDataSource hikariDataSource) {
        super(hikariDataSource);
    }

    @Override
    public List<BlockbusterLog> findEntry(int amount) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(BLOCKBUSTERLOG_REQUEST_QUERY);
            stmt.setInt(1, amount);
            ResultSet resultSet = stmt.executeQuery();
            return this.resultSetToEntityList(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error showing the log", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected BlockbusterLog toEntity(ResultSet resultSet) throws SQLException {
        return new BlockbusterLog(
                resultSet.getInt("id"),
                resultSet.getString("table_name"),
                resultSet.getDate("created_on"),
                resultSet.getString("entry_text"));
    }

    @Override
    public List<BlockbusterLog> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<BlockbusterLog> findById(Integer entityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public BlockbusterLog save(BlockbusterLog entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void delete(Integer entityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public BlockbusterLog update(BlockbusterLog entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
