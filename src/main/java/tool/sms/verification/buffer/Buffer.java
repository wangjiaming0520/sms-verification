package tool.sms.verification.buffer;

public interface Buffer {

    /**
     * 尝试添加key，如果存在则返回false
     * @param key key
     * @param duration duration
     * @return boolean
     */
    boolean addTransient(String key, long duration);

    /**
     * 获取key的值并删除key
     * @param key key
     * @return String
     */
    String getAndDelete(String key);

    /**
     * 自增key的值并获得，按天统计
     * @param key key
     * @return long
     */
    long getAndIncrementTransientByDay(String key);

    /**
     * 设置key值和过期时间
     * @param key key
     * @param value
     * @param duration duration
     * @return boolean
     */
    boolean setTransient(String key, String value, long duration);
}
