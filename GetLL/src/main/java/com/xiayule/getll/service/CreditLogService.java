package com.xiayule.getll.service;

import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-7-23.
 */
public interface CreditLogService {
    /**
     * 按顺序依次记录日志
     * @param mobile
     * @param content
     */
    public void log(String mobile, String content);

    /**
     * 在日志list最前插入一条日志
     * @param mobile
     * @param content
     */
    public void logHead(String mobile, String content);

    /**
     * 获得的格式为(哈希键是日期，值是日志列表):
     * {
     *     2014-07-20:[..., ..., ...],
     *     2014-08-21:[..., ..., ...]
     * }
     * @param mobile
     * @return
     */
    public Map<String, List<String>> readLog(String mobile);
}
