package tool.sms.verification;

import tool.sms.verification.buffer.Buffer;
import tool.sms.verification.generator.RandomCodeGenerator;

public class SmsValidator {

    /**
     * 验证码有效时间
     */
    private final long verifyCodeExpireTime;

    /**
     * 最小发送时间间隔
     */
    private final long sendInterval;

    /**
     * 每天最大发送次数
     */
    private final int maximumSendCountPerDay;

    /**
     * 缓存器实例
     */
    private final Buffer buffer;

    /**
     * 随机码生成器
     */
    private final RandomCodeGenerator randomCodeGenerator;

    /**
     * 文本模板
     */
    private final String contentTemplate;

    /**
     * 随机码替换占位符
     */
    private final String randomCodePlaceHolder;

    protected SmsValidator(long verifyCodeExpireTime, long sendInterval, int maximumSendCountPerDay, Buffer buffer, RandomCodeGenerator randomCodeGenerator, String contentTemplate, String randomCodePlaceHolder) {
        this.verifyCodeExpireTime = verifyCodeExpireTime;
        this.sendInterval = sendInterval;
        this.maximumSendCountPerDay = maximumSendCountPerDay;
        this.buffer = buffer;
        this.randomCodeGenerator = randomCodeGenerator;
        this.contentTemplate = contentTemplate;
        this.randomCodePlaceHolder = randomCodePlaceHolder;
    }

    public String sendRandomCode(String mobile) throws Exception {
        if (this.buffer.add(this.getLockKey(mobile), this.sendInterval)) {
            if (this.buffer.getAndIncrement(this.getSendCountKey(mobile)) > this.maximumSendCountPerDay) {
                throw new Exception("The number of sending times has reached the upper limit today.");
            }

            String randomCode = this.randomCodeGenerator.generate();
            this.buffer.set(this.getRandomCodeKey(mobile), randomCode, this.verifyCodeExpireTime);
            String content = new String(this.contentTemplate);
            content.replaceAll(this.randomCodePlaceHolder, randomCode);

            // TODO Send SMS

            return randomCode;
        } else {
            throw new Exception("Send too often, please try later.");
        }
    }

    public boolean validateRandomCode(String mobile, String randomCode) {
        String correct = this.buffer.delete(this.getRandomCodeKey(mobile));
        return randomCode.equals(correct);
    }

    private static final String PREFIX_LOCK = "SMS_VERIFICATION_LOCK_";

    private String getLockKey(String mobile) {
        return PREFIX_LOCK + mobile;
    }

    private static final String PREFIX_SEND_COUNT = "SMS_VERIFICATION_SEND_COUNT_";

    private String getSendCountKey(String mobile) {
        return PREFIX_SEND_COUNT + mobile;
    }

    private static final String PREFIX_RANDOM_CODE = "SMS_VERIFICATION_RANDOM_CODE_";

    private String getRandomCodeKey(String mobile) {
        return PREFIX_RANDOM_CODE + mobile;
    }
}
