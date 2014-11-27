package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.HistoryVersion;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public interface HistoryVersionService {
    public HistoryVersion getVersionHistory(Integer id);

    /**
     * 更新 historyVersion
     */
    public void saveVersionHistory(HistoryVersion historyVersion);

    public void deleteVersionHistory(Integer id);

    public void deleteVersionHistory(HistoryVersion historyVersion);

    public List<HistoryVersion> findAllVersionHistory();
}
