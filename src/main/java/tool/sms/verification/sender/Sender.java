package tool.sms.verification.sender;

public interface Sender {

    boolean send(String mobile, String content);
}
