package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.ShakeLog;

/**
 * Created by tan on 14-12-3.
 */
public interface ShakeLogService {
    /**
     * 记录日志
     * @param mobile 要记录的手机号
     * @param credit 摇到的手机号
     */
    public void log(String mobile, double credit);
}
