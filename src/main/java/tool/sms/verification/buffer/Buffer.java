package tool.sms.verification.buffer;

public interface Buffer {

    boolean add(String key, long duration);

    String delete(String key);

    String get(String key);

    int getAndIncrement(String key);

    boolean set(String key, String value, long duration);
}
