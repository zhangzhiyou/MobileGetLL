package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.VersionHistory;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public interface HistoryVersionService {
    public VersionHistory getVersionHistory(Integer id);

    /**
     * 更新 versionHistory
     */
    public void saveVersionHistory(VersionHistory versionHistory);

    public void deleteVersionHistory(Integer id);

    public void deleteVersionHistory(VersionHistory versionHistory);

    public List<VersionHistory> findAllVersionHistory();
}
