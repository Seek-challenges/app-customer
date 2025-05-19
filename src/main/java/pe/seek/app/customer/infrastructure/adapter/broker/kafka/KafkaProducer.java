package pe.seek.app.customer.infrastructure.adapter.broker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pe.seek.app.customer.application.port.output.CustomerCreatedPublisherPort;

import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.infrastructure.adapter.broker.events.CreatedCustomerEvent;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static pe.seek.app.shared.constants.TopicConstant.CREATE_CUSTOMER_BY_FALLBACK;

@Slf4j
@Component
@Profile({"dev"})
@RequiredArgsConstructor
class KafkaProducer implements CustomerCreatedPublisherPort {

    private final KafkaTemplate<String, CreatedCustomerEvent> kafkaTemplate;

    @Override
    public void notifyCreatedCustomerFallBack(Customer customer) {
        CreatedCustomerEvent notification = CreatedCustomerEvent.builder()
                .birthDate(String.valueOf(customer.getBirthDate()))
                .age(String.valueOf(customer.getAge()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .build();

        kafkaTemplate.send(CREATE_CUSTOMER_BY_FALLBACK, notification)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Failed to send message to SQS queue", exception);
                    } else {
                        log.info("Message sent to SQS queue successfully: {}", result.getRecordMetadata().offset());
                    }
                });
    }
}
