package exploringaxon.eventhandler;

import exploringaxon.api.event.AccountCreditedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.Instant;

/**
 * Created by Dadepo Aderemi.
 */
@Component
@ProcessingGroup("normalProcessor")
public class AccountCreditedEventHandler {

    @Autowired
    DataSource dataSource;

    @EventHandler
        public void handleAccountCreditedEvent(AccountCreditedEvent event, Message eventMessage, @Timestamp Instant moment) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Get the current states as reflected in the event
        String accountNo = event.getAccountNo();
        Double balance = event.getBalance();
        Double amountCredited = event.getAmountCredited();
        Double newBalance = balance + amountCredited;

        // Update the view
        String updateQuery = "UPDATE account_view SET balance = ? WHERE account_no = ?";
        jdbcTemplate.update(updateQuery, new Object[]{newBalance, accountNo});

        System.out.println("Events Handled With EventMessage " + eventMessage.toString() + " at " + moment.toString());
    }

}
