package exploringaxon;

import exploringaxon.model.Account;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Dadepo Aderemi.
 */
@Configuration
@EnableAxon
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("sa")
                .password("")
                .url("jdbc:h2:mem:exploredb")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean EventStore embeddedEventStore() throws SQLException {
        return new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }

    @Bean
    public SimpleCommandBus commandBus() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        return simpleCommandBus;
    }


    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    /**
     * Our aggregate root is now created from stream of events and not from a representation in a persistent mechanism,
     * thus we need a repository that can handle the retrieving of our aggregate root from the stream of events.
     *
     * We configure the EventSourcingRepository which does exactly this. We supply it with the event store
     * @return a {@link EventSourcingRepository} implementation of {@link Repository}
     */
    @Bean
    public Repository<Account> eventSourcingRepository() throws SQLException {
        EventSourcingRepository eventSourcingRepository = new EventSourcingRepository(Account.class, embeddedEventStore());
        return eventSourcingRepository;
    }

    /**
     * Component that allows an aggregate to directly act as command handlers for commands.
     *
     * @return an instance of {@link AggregateAnnotationCommandHandler}
     */
    @Bean
    public AggregateAnnotationCommandHandler aggregateAnnotationCommandHandler() throws SQLException {
        AggregateAnnotationCommandHandler<Account> handler = new AggregateAnnotationCommandHandler<>(Account.class, eventSourcingRepository());
        for (String supportedCommand : handler.supportedCommandNames()) {
            commandBus().subscribe(supportedCommand, handler);
        }
        return handler;
    }
}
