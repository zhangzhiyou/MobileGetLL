package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CreditLogService;
import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tan on 14-7-23.
 * 用来记录获奖记录
 *
 * 使用方法:
 * creditLogService.log("110", "开始服务");
 * creditLogService.log("110", "开始了");

 * Map<String, List<String>> rs = creditLogService.readLog("110");

 * Set<String> keys = rs.keySet();

 * for (String key : keys) {
 *   System.out.println(key + ":");
 *   List<String> list = rs.get(key);
 *   for (String s : list) {
 *     System.out.println(s);
 *   }
 * }
 */

public class CreditLogServiceImpl implements CreditLogService {
    private RedisService redisService;

    public void log(String mobile, String content) {
        String date = TimeUtils.getDate();
        redisService.rpush("log_" + mobile + "_" + date, content);
    }

    public void logHead(String mobile, String content) {
        String date = TimeUtils.getDate();
        redisService.lpush("log_" + mobile + "_" + date, content);
    }

    /**
     * 返回的格式为(哈希键是日期，值是日志列表, 自动按时间 新->旧 排序):
     * {
     *     2014-08-21:[..., ..., ...]
     *     2014-07-20:[..., ..., ...],
     * }
     * @param mobile
     * @return
     */
    public Map<String, List<String>> readLog(String mobile) {
        // 前缀
        String prefix = "log_" + mobile + "_";

        Set<String> keys = redisService.keys(prefix+"*");

        Map<String, List<String>> rs = new HashMap<String, List<String>>();

        for (String key : keys) {
            List<String> logs = redisService.lrange(key, 0, -1);

            // 去掉前缀就是日期
            String date = key.replace(prefix, "");
            rs.put(date, logs);
        }

        return rs;
    }

    // set and get methods


    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }


}
