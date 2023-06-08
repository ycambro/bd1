package tec.bd.repository;

import com.zaxxer.hikari.HikariDataSource;
import tec.bd.entities.Client;
import tec.bd.entities.Review;
import tec.bd.entities.Movie;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tec.bd.repository.Queries.*;

public class ReviewRepositoryImpl extends BaseRepository<Review, Integer> implements ReviewRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    public ReviewRepositoryImpl(HikariDataSource hikariDataSource) {
        super(hikariDataSource);
    }

    @Override
    public List<Review> findAll() {
        try {
            return this.query(REVIEW_FIND_ALL_QUERY);
        } catch (SQLException e) {
            LOGGER.error("Error when finding Review in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Review> findById(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(REVIEW_FIND_BY_ID_QUERY);
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
    public Review save(Review entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(REVIEW_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, entity.getRating());
                stmt.setString(2, entity.getReviewText());
                stmt.setDate(3, new java.sql.Date(entity.getCreatedOn().getTime()));
                stmt.setInt(4, entity.getClient().getClientId());
                stmt.setInt(5, entity.getMovie().getMovieId());
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
            LOGGER.error("Error when saving review in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(REVIEW_DELETE_REVIEW_ID);
            stmt.setInt(1, entityId);
            int affectedRows = stmt.executeUpdate();
            LOGGER.debug("Delete rent, affected rows {}", affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Error when deleting review by Id in the DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Review update(Review entity) {
        try (var conn = this.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(REVIEW_UPDATE_REVIEW)) {
                stmt.setInt(1, entity.getRating());
                stmt.setString(2, entity.getReviewText());
                stmt.setDate(3, new java.sql.Date(entity.getCreatedOn().getTime()));
                stmt.setInt(4, entity.getClient().getClientId());
                stmt.setInt(5, entity.getMovie().getMovieId());
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
            LOGGER.error("Error when updating review in the DB", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected Review toEntity(ResultSet resultSet) throws SQLException {
        var movie = new Movie();
        movie.setMovieId(resultSet.getInt("movie_id"));
        var client = new Client();
        client.setClientId(resultSet.getInt("client_id"));
        return new Review(
                resultSet.getInt("id"),
                resultSet.getInt("rating"),
                resultSet.getString("review_text"),
                resultSet.getDate("created_on"),
                movie, client);
    }

}
