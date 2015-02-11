package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.MobileAccount;

/**
 * Created by tan on 15-2-11.
 */
public interface MobileAccountService {
    public void save(MobileAccount mobileAccount);

    public void update(MobileAccount mobileAccount);

    public MobileAccount getByMobile(String mobile);

    public void updateEndTime(String mobile);

    /**
     * 统计当前有效用户的数量
     * @return
     */
    public Long countValid();
}
