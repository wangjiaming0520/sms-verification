package tool.sms.verification;

import tool.sms.verification.buffer.Buffer;
import tool.sms.verification.generator.DefaultRandomCodeGenerator;
import tool.sms.verification.generator.RandomCodeGenerator;
import tool.sms.verification.sender.Sender;

public class SmsValidatorBuilder {

    private static final long DEFAULT_VERIFY_CODE_EXPIRE_TIME = 5 * 60 * 1000L;
    private static final long DEFAULT_SEND_INTERVAL = 60 * 1000L;
    private static final int DEFAULT_MAXIMUM_SEND_COUNT_PER_DAY = 5;

    private static final Class<? extends RandomCodeGenerator> DEFAULT_RANDOM_CODE_GENERATOR = DefaultRandomCodeGenerator.class;

    /**
     * 验证码有效时间
     */
    private long verifyCodeExpireTime = DEFAULT_VERIFY_CODE_EXPIRE_TIME;

    /**
     * 最小发送时间间隔
     */
    private long sendInterval = DEFAULT_SEND_INTERVAL;

    /**
     * 每天最大发送次数
     */
    private int maximumSendCountPerDay = DEFAULT_MAXIMUM_SEND_COUNT_PER_DAY;

    /**
     * 缓存器实例
     */
    private Buffer buffer;

    /**
     * 随机码生成器
     */
    private RandomCodeGenerator randomCodeGenerator;

    /**
     * 短信发送器
     */
    private Sender sender;

    /**
     * 文本模板
     */
    private String contentTemplate;

    /**
     * 随机码替换占位符
     */
    private String randomCodePlaceHolder;

    public SmsValidator build() throws Exception {
        if (this.contentTemplate == null) {
            throw new Exception("contentTemplate is null.");
        }
        if (this.randomCodePlaceHolder == null) {
            throw new Exception("randomCodePlaceHolder is null.");
        }
        if (this.buffer == null) {
            throw new Exception("buffer is null.");
        }
        if (this.sender == null) {
            throw new Exception("sender is null.");
        }
        if (this.randomCodeGenerator == null) {
            this.randomCodeGenerator = DEFAULT_RANDOM_CODE_GENERATOR.newInstance();
        }
        return new SmsValidator(verifyCodeExpireTime, sendInterval, maximumSendCountPerDay, buffer, randomCodeGenerator, sender, contentTemplate, randomCodePlaceHolder);
    }

    public SmsValidatorBuilder verifyCodeExpireTime(long verifyCodeExpireTime) {
        this.verifyCodeExpireTime = verifyCodeExpireTime;
        return this;
    }

    public SmsValidatorBuilder sendInterval(long sendInterval) {
        this.sendInterval = sendInterval;
        return this;
    }

    public SmsValidatorBuilder maximumSendCountPerDay(int maximumSendCountPerDay) {
        this.maximumSendCountPerDay = maximumSendCountPerDay;
        return this;
    }

    public SmsValidatorBuilder buffer(Buffer buffer) {
        this.buffer = buffer;
        return this;
    }

    public SmsValidatorBuilder randomCodeGenerator(RandomCodeGenerator randomCodeGenerator) {
        this.randomCodeGenerator = randomCodeGenerator;
        return this;
    }

    public SmsValidatorBuilder sender(Sender sender) {
        this.sender = sender;
        return this;
    }

    public SmsValidatorBuilder contentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
        return this;
    }

    public SmsValidatorBuilder randomCodePlaceHolder(String randomCodePlaceHolder) {
        this.randomCodePlaceHolder = randomCodePlaceHolder;
        return this;
    }

    public static SmsValidatorBuilder getNew() {
        return new SmsValidatorBuilder();
    }

    private SmsValidatorBuilder() {
    }
}
