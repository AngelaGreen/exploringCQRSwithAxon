package exploringaxon.replay;

import exploringaxon.api.event.AccountCreditedEvent;
import exploringaxon.api.event.AccountDebitedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Component
@ProcessingGroup("replayProcessor")
public class AccountCreditedReplayEventHandler {

    List<String> audit = new ArrayList<>();

    @EventHandler
    public void handle(AccountCreditedEvent event) {
        String auditMsg = String.format("%s credited to account with account no {%s} on %s",
                event.getAmountCredited(), event.getAccountNo(), formatTimestampToString(event.getTimeStamp()));
        audit.add(auditMsg);
    }

    @EventHandler
    public void handle(AccountDebitedEvent event) {
        String auditMsg = String.format("%s debited from account with account no {%s} on %s",
                event.getAmountDebited(), event.getAccountNo(), formatTimestampToString(event.getTimeStamp()));
        audit.add(auditMsg);
    }

    public List<String> getAudit() {
        return audit;
    }

    public void beforeReplay() {
        audit.clear();
    }

    public void afterReplay() {
    }

    public void onReplayFailed(Throwable cause) {}

    private String formatTimestampToString(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp * 1000);
    }
}
