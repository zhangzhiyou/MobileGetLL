package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.VersionHistoryDao;
import com.xiayule.getll.db.model.HistoryVersion;
import com.xiayule.getll.db.service.HistoryVersionService;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public class HistoryVersionServiceImpl implements HistoryVersionService {
    private VersionHistoryDao versionHistoryDao;

    public HistoryVersion getVersionHistory(Integer id) {
        return versionHistoryDao.get(id);
    }

    /**
     * 更新 historyVersion
     */
    public void saveVersionHistory(HistoryVersion historyVersion) {
        versionHistoryDao.saveOrUpdate(historyVersion);
    }


    public void deleteVersionHistory(Integer id) {
        versionHistoryDao.delete(id);
    }

    public void deleteVersionHistory(HistoryVersion historyVersion) {
        versionHistoryDao.delete(historyVersion);
    }

    public List<HistoryVersion> findAllVersionHistory() {
        return versionHistoryDao.findAllVersionHistory();
    }

    // set and get methods


    public VersionHistoryDao getVersionHistoryDao() {
        return versionHistoryDao;
    }

    public void setVersionHistoryDao(VersionHistoryDao versionHistoryDao) {
        this.versionHistoryDao = versionHistoryDao;
    }
}
