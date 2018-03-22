import org.springframework.data.redis.core.RedisTemplate;
import tool.sms.verification.SmsValidator;
import tool.sms.verification.SmsValidatorBuilder;
import tool.sms.verification.buffer.RedisOperationsBuffer;
import tool.sms.verification.exception.SmsSendFailureException;

public class Demo {


    public static void main(String[] args) {
        SmsValidatorBuilder builder = SmsValidatorBuilder.getNew();
        try {
            SmsValidator smsValidator = builder
                    .maximumSendCountPerDay(5)
                    .verifyCodeExpireTime(5 * 60 * 1000L)
                    .sendInterval(60 * 1000L)
                    .contentTemplate("验证码是${code}。") // 短信内容模板
                    .randomCodePlaceHolder("${code}") // 上面的占位符
                    .buffer(new RedisOperationsBuffer(new RedisTemplate())) // redisTemplate从spring容器中获取
                    .sender((String mobile, String content) -> {
                            // 发送实现
                            System.out.println("发送短信至【" + mobile + "】, 内容【" + content + "】。");
                            return true;
                        })
                    .build();

            String mobile = "11111111111";
            String randomCode = null;

            try {
                // 发送验证码
                randomCode = smsValidator.sendRandomCode(mobile); // 方法返回验证码但是不需要使用
            } catch (SmsSendFailureException e) {
                System.out.println("发送失败, " + e);
            }

            // 校验
            boolean validated = smsValidator.validateRandomCode(mobile, randomCode); // 这里的randomCode理应从页面表单提交
            System.out.println("验证结果：" +validated);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
