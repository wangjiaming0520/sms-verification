package tool.sms.verification.buffer;

import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.TimeUnit;

public class RedisOperationsBuffer implements Buffer {

    private final RedisOperations redisOperations;

    private static final Object PRESENT = new Object();

    public RedisOperationsBuffer(RedisOperations redisOperations) {
        this.redisOperations = redisOperations;
    }

    @Override
    public boolean addTransient(String key, long duration) {
        boolean result = this.redisOperations.opsForValue().setIfAbsent(key, PRESENT);
        if (result) {
            this.redisOperations.expire(key, duration, TimeUnit.MILLISECONDS);
        }
        return result;
    }

    @Override
    public String getAndDelete(String key) {
        String value = String.valueOf(this.redisOperations.opsForValue().get(key));
        this.redisOperations.delete(key);
        return value;
    }

    @Override
    public long getAndIncrementTransientByDay(String key) {
        long value = this.redisOperations.opsForValue().increment(key, 1L);
        if (value == 1L) {
            this.redisOperations.expire(key, 1, TimeUnit.DAYS);
        }
        return value;
    }

    public boolean setTransient(String key, String value, long duration) {
        this.redisOperations.opsForValue().set(key, value, duration, TimeUnit.MILLISECONDS);
        return true;
    }
}
