package tool.sms.verification.exception;

public class SmsSendFailureException extends RuntimeException {

    public SmsSendFailureException(String cause) {
        super(cause);
    }

}
