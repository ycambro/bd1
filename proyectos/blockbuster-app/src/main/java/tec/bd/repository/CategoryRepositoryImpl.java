package tec.bd.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static tec.bd.repository.Queries.*;

public class CategoryRepositoryImpl extends BaseRepository<Category, Integer> implements CategoryRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    public CategoryRepositoryImpl(HikariDataSource hikariDataSource) {
        super(hikariDataSource);
    }

    @Override
    public List<Category> findAll() {
        try {
            return this.query(CATEGORY_FIND_ALL_QUERY);
        } catch (SQLException e) {
            LOGGER.error("Error when finding categories in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findById(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(CATEGORY_FIND_BY_ID_QUERY);
            stmt.setInt(1, entityId);
            return this.query(stmt)
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            LOGGER.error("Error when finding category by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category save(Category entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(CATEGORY_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, entity.getCategoryName());
                stmt.setString(2, entity.getDescription());
                var rowsAffected = stmt.executeUpdate(); // se realiza la operacion de escritura
                conn.commit();

                LOGGER.debug("Rows Affected {}", rowsAffected);

                var resultSet = stmt.getGeneratedKeys();

                if (resultSet.next()) {
                    var lastInsertedId = resultSet.getInt(1);
                    LOGGER.debug("Last insert id {}", lastInsertedId);
                    return this.findById(lastInsertedId).get();
                }
            } catch (SQLException e) {
                try {
                    LOGGER.debug("Transaction is being rolled out");
                    conn.rollback();
                } catch (Exception rex) {
                    LOGGER.error("Cant rollback savepointOne operation", rex);
                    throw new RuntimeException(rex);
                }
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.error("Error when saving category in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(CATEGORY_DELETE_CATEGORY_ID);
            stmt.setInt(1, entityId);
            int affectedRows = stmt.executeUpdate();
            LOGGER.debug("Delete Category, affected rows {}", affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Error when deleting category by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category update(Category entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(CATEGORY_UPDATE_CATEGORY)) {
                stmt.setString(1, entity.getCategoryName());
                stmt.setString(2, entity.getDescription());
                stmt.setInt(5, entity.getCategoryId());
                var rowsAffected = stmt.executeUpdate(); // se realiza la operacion de escritura
                conn.commit();

                LOGGER.debug("Rows Affected {}", rowsAffected);

                return entity;

            } catch (SQLException e) {
                try {
                    LOGGER.debug("Transaction is being rolled out");
                    conn.rollback();
                } catch (Exception rex) {
                    LOGGER.error("Cant rollback savepointOne operation", rex);
                    throw new RuntimeException(rex);
                }
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.error("Error when updating category in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected Category toEntity(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("category_name"),
                resultSet.getString("category_description"));
    }
}
