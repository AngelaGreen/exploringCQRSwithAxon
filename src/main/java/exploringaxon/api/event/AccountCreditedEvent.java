package exploringaxon.api.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Event Class that communicates that an account has been credited
 *
 * Created by Dadepo Aderemi.
 */
public class AccountCreditedEvent {

    private final String accountNo;
    private final Double amountCredited;
    private final Double balance;
    private final long timeStamp;

    public AccountCreditedEvent(String accountNo, Double amountCredited, Double balance) {
        this.accountNo = accountNo;
        this.amountCredited = amountCredited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Double getAmountCredited() {
        return amountCredited;
    }

    public Double getBalance() {
        return balance;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
