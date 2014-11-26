package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.VersionHistory;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public interface VersionHistoryDao {
    public VersionHistory get(Integer id);

    public Integer save(VersionHistory versionHistory);

    public void update(VersionHistory versionHistory);

    public void delete(Integer id);

    public void delete(VersionHistory versionHistory);

    public List<VersionHistory> findAllVersionHistory();
}
