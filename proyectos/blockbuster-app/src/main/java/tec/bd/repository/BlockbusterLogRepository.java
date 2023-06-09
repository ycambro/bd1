package tec.bd.repository;

import java.util.List;

import tec.bd.entities.BlockbusterLog;

public interface BlockbusterLogRepository extends CRUDRepository<BlockbusterLog, Integer> {
    public List<BlockbusterLog> findEntry(int amount);

}
