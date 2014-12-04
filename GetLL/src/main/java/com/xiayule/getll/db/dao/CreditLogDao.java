package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.CreditLog;

import java.util.List;

/**
 * Created by tan on 14-12-3.
 */
public interface CreditLogDao {
    public CreditLog get(Integer id);

    public void saveOrUpdate(CreditLog creditLog);

    public void update(CreditLog creditLog);

    public void delete(Integer id);

    public void delete(CreditLog creditLog);

    public List<CreditLog> findAllVersionHistory();
}
