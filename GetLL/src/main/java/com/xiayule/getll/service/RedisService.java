package com.xiayule.getll.service;

import java.util.Map;
import java.util.Set;

/**
 * Created by tan on 14-7-21.
 */
public interface RedisService {
    /**
     * 将哈希表 key 中的域 field 的值设为 value
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash);

    /**
     * 返回哈希表 key 中，所有的域和值
     */
    public Map hgetAll(String key);

    /**
     * 检查给定 key 是否存在。
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * 查看哈希表 key 中，给定 field 是否存在
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field);

    /**
     * 将一个或多个 member 元素加入到集合 key 当中
     *
     * @param key
     * @param member
     */
    public void sadd(String key, String member);
    /**
     * 返回集合中元素的数量
     *
     * @param key
     * @return
     */
    public Long scard(String key);

    /**
     * 判断 member 元素是否集合 key 的成员。
     *
     * @param key
     * @param member
     */
    public void sismember(String key, String member);

    /**
     * 返回集合 key 中的所有成员。
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) ;

    /**
     * 将 member 元素从 source 集合移动到 destination 集合。
     * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
     * 当 source 或 destination 不是集合类型时，返回一个错误。
     *
     * @param source
     * @param destination
     * @param member
     */
    public Long smove(String source, String destination, String member);

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * @param key
     * @param member
     * @return
     */
    public Long srem(String key, String member);

    /**
     * 将字符串值 value 关联到 key 。
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param key
     * @param seconds
     * @param value
     * @return 设置成功时返回 OK,当 seconds 参数不合法时，返回一个错误。
     */
    public String setex(String key, int seconds, String value);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     * @param key
     * @return
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     */
    public Long ttl(String key);

    /**
     * 删除给定的一个或多个 key 。
     * 不存在的 key 会被忽略。
     * @param key
     * @return 被删除 key 的数量。
     */
    public Long del(String key);

    /**
     * 查找所有符合给定模式 pattern 的 key
     * @param pattern
     * * 匹配数据库中所有 key
     * h?llo 匹配 hello ， hallo 和 hxllo 等
     * h*llo 匹配 hllo 和 heeeeello 等
     * h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo
     */
    public Set<String> keys(String pattern);
}
