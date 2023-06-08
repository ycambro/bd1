package tec.bd.repository;

import tec.bd.entities.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CRUDRepository<Movie, Integer> {

    public List<Movie> findAll();

    public Optional<Movie> findById(Integer entityId);

    public Movie save (Movie entity);

    public void delete(Integer entityId);

    public Movie update(Movie entity);

}
