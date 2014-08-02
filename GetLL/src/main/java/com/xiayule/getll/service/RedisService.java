package com.xiayule.getll.service;

import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tan on 14-7-21.
 */
public interface RedisService {
    /**
     * 将哈希表 key 中的域 field 的值设为 value
     *
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
     *
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * 查看哈希表 key 中，给定 field 是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field);

    /**
     * 将一个或多个 member 元素加入到集合 key 当中
     *  @param key
     * @param member
     */
    public Long sadd(String key, String member);

    /**
     * 返回集合中元素的数量
     *
     * @param key
     * @return
     */
    public Long scard(String key);

    /**
     * 判断 member 元素是否集合 key 的成员。
     *  @param key
     * @param member
     */
    public boolean sismember(String key, String member);

    /**
     * 返回集合 key 中的所有成员。
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key);

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
     *
     * @param key
     * @param member
     * @return
     */
    public Long srem(String key, String member);

    /**
     * 将字符串值 value 关联到 key 。
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     *
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param key
     * @param seconds
     * @param value
     * @return 设置成功时返回 OK,当 seconds 参数不合法时，返回一个错误。
     */
    public String setex(String key, int seconds, String value);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     *
     * @param key
     * @return 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     */
    public Long ttl(String key);

    /**
     * 删除给定的一个或多个 key 。
     * 不存在的 key 会被忽略。
     *
     * @param key
     * @return 被删除 key 的数量。
     */
    public Long del(String key);

    /**
     * 查找所有符合给定模式 pattern 的 key
     *
     * @param pattern * 匹配数据库中所有 key
     *                h?llo 匹配 hello ， hallo 和 hxllo 等
     *                h*llo 匹配 hllo 和 heeeeello 等
     *                h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo
     */
    public Set<String> keys(String pattern);

    /**
     * 将一个值 value 插入到列表 key 的<strong>表头</strong>
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param key
     * @param value
     * @return 执行 LPUSH 命令后，列表的长度。
     */
    public Long lpush(String key, String value);

    /**
     * 将一个值 value 插入到列表 key 的表尾(最右边)。
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param key
     * @param value
     * @return 执行 RPUSH 操作后，表的长度。
     */
    public Long rpush(String key, String value);

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * <p/>
     * 返回全部元素可以 (0,-1)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end);

    /**
     * 将一个 member 元素及其 score 值加入到有序集 key 当中。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member);

    /**
     * 返回有序集 key 的基数。
     *
     * @param key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0 。
     */
    public Long zcard(String key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, double min, double max);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
     * <p/>
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
     * 当 key 不是有序集类型时，返回一个错误。
     * score 值可以是整数值或双精度浮点数。
     *
     * @param key
     * @param score
     * @param member
     * @return member 成员的新 score 值，以字符串形式表示。
     */
    public Double zincrby(String key, double score, String member);


    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。
     * 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, int start, int end);

    /**
     * 通 zrange，但是返回score
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(String key, int start, int end);

    /**
     * 详情参见 zrange
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, int start, int end);

    /**
     * 详情参见 zrangeWithScores
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrevrangeWithScores(String key, int start, int end);

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * 在 Redis 中，带有生存时间的 key 被称为『易失的』(volatile)。
     * <p/>
     * 生存时间可以通过使用 DEL 命令来删除整个 key 来移除，或者被 SET 和 GETSET 命令覆写(overwrite)，这意味着，如果一个命令只是修改(alter)一个带生存时间的 key 的值而不是用一个新的 key 值来代替(replace)它的话，那么生存时间不会被改变。
     * 比如说，对一个 key 执行 INCR 命令，对一个列表进行 LPUSH 命令，或者对一个哈希表执行 HSET 命令，这类操作都不会修改 key 本身的生存时间。
     * <p/>
     * 另一方面，如果使用 RENAME 对一个 key 进行改名，那么改名后的 key 的生存时间和改名前一样。
     * <p/>
     * RENAME 命令的另一种可能是，尝试将一个带生存时间的 key 改名成另一个带生存时间的 another_key ，这时旧的 another_key (以及它的生存时间)会被删除，然后旧的 key 会改名为 another_key ，因此，新的 another_key 的生存时间也和原本的 key 一样。
     * <p/>
     * 使用 PERSIST 命令可以在不删除 key 的情况下，移除 key 的生存时间，让 key 重新成为一个『持久的』(persistent) key 。
     * <p/>
     * 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间。
     * <p/>
     * 设置成功返回 1 。
     * 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     *
     * @param key
     * @param seconds
     * @return 成功1, 失败0
     */
    public Long expire(String key, int seconds);

    /**
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。
     * 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
     *
     * @param key
     * @param unixTime
     * @return 成功返回1, 失败返回 0
     */
    public Long expireat(String key, long unixTime);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
     *
     * @param key
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 nil 。
     */
    public Long zrank(String key, String member);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
     *
     * @param key
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名;如果 member 不是有序集 key 的成员，返回 nil 。
     */
    public Long zrevrank(String key, String member);

    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member);

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     * @param key
     * @param field
     * @param value
     */
    public Long hset(String key, String field, String value);

    /**
     * 返回 key 所关联的字符串值。
     * 如果 key 不存在那么返回特殊值 nil 。
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
     * @param key 键
     * @return 值
     */
    public String get(String key);
}
