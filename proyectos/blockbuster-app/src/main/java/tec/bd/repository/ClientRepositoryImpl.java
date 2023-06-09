package tec.bd.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static tec.bd.repository.Queries.*;

public class ClientRepositoryImpl extends BaseRepository<Client, Integer> implements ClientRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    public ClientRepositoryImpl(HikariDataSource hikariDataSource) {
        super(hikariDataSource);
    }

    @Override
    public List<Client> findAll() {
        try {
            return this.query(CLIENTE_FIND_ALL_QUERY);
        } catch (SQLException e) {
            LOGGER.error("Error when finding clients in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> findById(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(CLIENTE_FIND_BY_ID_QUERY);
            stmt.setInt(1, entityId);
            return this.query(stmt)
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            LOGGER.error("Error when finding client by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client save(Client entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(CLIENTE_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getLastname());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getPhoneNumber());
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
            LOGGER.error("Error when saving Client in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(CLIENTE_DELETE_CLIENT_ID);
            stmt.setInt(1, entityId);
            int affectedRows = stmt.executeUpdate();
            LOGGER.debug("Delete client, affected rows {}", affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Error when deleting client by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client update(Client entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(CLIENTE_UPDATE_CLIENT)) {
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getLastname());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getPhoneNumber());
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
            LOGGER.error("Error when updating client in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected Client toEntity(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("client_name"),
                resultSet.getString("lastname"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"));
    }
}
