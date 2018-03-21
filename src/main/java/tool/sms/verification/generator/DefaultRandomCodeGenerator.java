package tool.sms.verification.generator;

public class DefaultRandomCodeGenerator implements RandomCodeGenerator {

    private final int randomCodeLength = 6;
    public String generate() {
        String code = String.valueOf(System.currentTimeMillis());
        return code.substring(code.length() - randomCodeLength);
    }
}
