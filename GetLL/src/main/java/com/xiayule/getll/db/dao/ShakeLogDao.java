package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.ShakeLog;

import java.util.List;

/**
 * Created by tan on 14-12-3.
 */
public interface ShakeLogDao {
    public ShakeLog get(Integer id);

    public void saveOrUpdate(ShakeLog shakeLog);

    public void update(ShakeLog shakeLog);

    public void delete(Integer id);

    public void delete(ShakeLog shakeLog);

    public List<ShakeLog> findAllVersionHistory();
}
