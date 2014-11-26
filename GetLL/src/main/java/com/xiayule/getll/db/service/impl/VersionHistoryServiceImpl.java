package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.VersionHistoryDao;
import com.xiayule.getll.db.model.VersionHistory;
import com.xiayule.getll.db.service.VersionHistoryService;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public class VersionHistoryServiceImpl implements VersionHistoryService {
    private VersionHistoryDao versionHistoryDao;

    public VersionHistory getVersionHistory(Integer id) {
        return versionHistoryDao.get(id);
    }

    /**
     * 更新 versionHistory
     */
    public void saveVersionHistory(VersionHistory versionHistory) {
        versionHistoryDao.saveOrUpdate(versionHistory);
    }


    public void deleteVersionHistory(Integer id) {
        versionHistoryDao.delete(id);
    }

    public void deleteVersionHistory(VersionHistory versionHistory) {
        versionHistoryDao.delete(versionHistory);
    }

    public List<VersionHistory> findAllVersionHistory() {
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
