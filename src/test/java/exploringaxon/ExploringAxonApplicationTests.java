package exploringaxon;

import exploringaxon.api.command.CreditAccountCommand;
import exploringaxon.api.command.DebitAccountCommand;
import exploringaxon.api.event.AccountCreatedEvent;
import exploringaxon.api.event.AccountCreditedEvent;
import exploringaxon.api.event.AccountDebitedEvent;
import exploringaxon.model.Account;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExploringAxonApplication.class)
@WebAppConfiguration
public class ExploringAxonApplicationTests {

	private FixtureConfiguration fixture;
	private String accountNo = "test-acc";

	@Before
	public void setUp() {
		fixture = new AggregateTestFixture<>(Account.class);
    }

	@Test
	public void testFirstDeposit() {
		fixture.given(new AccountCreatedEvent(accountNo))
			   .when(new CreditAccountCommand(accountNo, 100.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 100.00, 0.0));

	}

	@Test
	public void testFirstSecondDeposit() {
		fixture.given(new AccountCreatedEvent(accountNo),
					  new AccountCreditedEvent(accountNo, 100.00, 0.0))
			   .when(new CreditAccountCommand(accountNo, 40.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 40.00, 100.00));
	}

	@Test
	public void testCreditingDebitingAndCrediting() {
		fixture.given(new AccountCreatedEvent(accountNo),
					  new AccountCreditedEvent(accountNo, 100.00, 0.0),
					  new AccountDebitedEvent(accountNo, 40.00, 100.0))
			   .when(new CreditAccountCommand(accountNo, 40.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 40.00, 60.00));
	}

	@Test
	public void cannotCreditWithAMoreThanMillion() {
		fixture.given(new AccountCreatedEvent(accountNo))
			   .when(new CreditAccountCommand(accountNo, 10000000.00))
			   .expectException(IllegalArgumentException.class);
	}

	@Test
	public void cannotDebitAccountWithZeroBalance() {
		fixture.given(new AccountCreatedEvent(accountNo))
			   .when(new DebitAccountCommand(accountNo, 1.0))
			   .expectException(IllegalArgumentException.class);
	}

}
