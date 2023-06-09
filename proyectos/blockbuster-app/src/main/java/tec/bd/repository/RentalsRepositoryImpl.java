package tec.bd.repository;

import com.zaxxer.hikari.HikariDataSource;
import tec.bd.entities.Client;
import tec.bd.entities.Rentals;
import tec.bd.entities.Movie;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tec.bd.repository.Queries.*;

public class RentalsRepositoryImpl extends BaseRepository<Rentals, Integer> implements RentalsRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(RentalsRepositoryImpl.class);

    public RentalsRepositoryImpl(HikariDataSource hikariDataSource) {
        super(hikariDataSource);
    }

    @Override
    public List<Rentals> findAll() {
        try {
            return this.query(RENTALS_FIND_ALL_QUERY);
        } catch (SQLException e) {
            LOGGER.error("Error when finding rentals in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Rentals> findById(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(RENTALS_FIND_BY_ID_QUERY);
            stmt.setInt(1, entityId);
            return this.query(stmt)
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            LOGGER.error("Error when finding rent by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rentals save(Rentals entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(RENTALS_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, new java.sql.Date(entity.getRentalDate().getTime()));
                stmt.setInt(2, entity.getClient().getClientId());
                stmt.setInt(3, entity.getMovie().getMovieId());
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
            LOGGER.error("Error when saving rent in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(RENTALS_DELETE_RENTALS_ID);
            stmt.setInt(1, entityId);
            int affectedRows = stmt.executeUpdate();
            LOGGER.debug("Delete rent, affected rows {}", affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Error when deleting rent by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rentals update(Rentals entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(RENTALS_UPDATE_RENTALS)) {
                stmt.setDate(1, new java.sql.Date(entity.getRentalDate().getTime()));
                stmt.setInt(2, entity.getClient().getClientId());
                stmt.setInt(3, entity.getMovie().getMovieId());
                stmt.setInt(4, entity.getRentId());
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
            LOGGER.error("Error when updating rent in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected Rentals toEntity(ResultSet resultSet) throws SQLException {
        var movie = new Movie();
        movie.setMovieId(resultSet.getInt("movie_id"));
        var client = new Client();
        client.setClientId(resultSet.getInt("client_id"));
        return new Rentals(
                resultSet.getInt("id"),
                resultSet.getDate("rental_date"),
                movie, client);
    }

}
