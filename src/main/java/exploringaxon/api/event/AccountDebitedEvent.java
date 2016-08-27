package exploringaxon.api.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Event Class that communicates that an account has been debited
 *
 * Created by Dadepo Aderemi.
 */
public class AccountDebitedEvent {
    private final String accountNo;
    private final Double amountDebited;
    private final Double balance;
    private final long timeStamp;

    public AccountDebitedEvent(String accountNo, Double amountDebited, Double balance) {
        this.accountNo = accountNo;
        this.amountDebited = amountDebited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Double getAmountDebited() {
        return amountDebited;
    }

    public Double getBalance() {
        return balance;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
