import tool.sms.verification.SmsValidator;
import tool.sms.verification.SmsValidatorBuilder;

public class Demo {

    public static void main(String[] args) {
        SmsValidatorBuilder builder = SmsValidatorBuilder.getNew();
        try {
            SmsValidator smsValidator = builder
                    .maximumSendCountPerDay(5)
                    .verifyCodeExpireTime(5 * 60 * 1000L)
                    .sendInterval(1 * 60 * 1000L)
                    .contentTemplate("验证码是${code}。") // 短信内容模板
                    .randomCodePlaceHolder("${code}") // 上面的占位符
//                .buffer() TODO 缓存接口待实现
                    .build();

            String mobile = "11111111111";
            // 发送验证码
            String randomCode = smsValidator.sendRandomCode(mobile); // 方法返回验证码但是不需要使用

            // 校验
            boolean validated = smsValidator.validateRandomCode(mobile, randomCode); // 这里的randomCode理应从页面表单提交
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
