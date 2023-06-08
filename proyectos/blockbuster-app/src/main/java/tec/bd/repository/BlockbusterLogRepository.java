package tec.bd.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import tec.bd.entities.BlockbusterLog;
import tec.bd.entities.Category;

public interface BlockbusterLogRepository extends CRUDRepository<BlockbusterLog, Integer> {
    public List<BlockbusterLog> findEntry(int amount);

}
