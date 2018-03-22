package tool.sms.verification.buffer;

public interface Buffer {

    boolean addTransient(String key, long duration);

    String getAndDelete(String key);

    long getAndIncrementTransientByDay(String key);

    boolean setTransient(String key, String value, long duration);
}
