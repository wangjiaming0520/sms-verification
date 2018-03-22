package tool.sms.verification.sender;

public interface Sender {

    /**
     * 发送信息实现
     * @param mobile mobile
     * @param content content
     * @return boolean
     */
    boolean send(String mobile, String content);
}
