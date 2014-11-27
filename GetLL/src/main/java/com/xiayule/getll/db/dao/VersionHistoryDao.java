package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.HistoryVersion;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public interface VersionHistoryDao {
    public HistoryVersion get(Integer id);

    public Integer save(HistoryVersion historyVersion);

    public void saveOrUpdate(HistoryVersion historyVersion);

    public void update(HistoryVersion historyVersion);

    public void delete(Integer id);

    public void delete(HistoryVersion historyVersion);

    public List<HistoryVersion> findAllVersionHistory();
}
