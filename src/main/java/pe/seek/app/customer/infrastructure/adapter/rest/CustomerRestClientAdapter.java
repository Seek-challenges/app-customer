package pe.seek.app.customer.infrastructure.adapter.rest;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.seek.app.customer.application.port.output.CustomerCreatedPublisherPort;
import pe.seek.app.customer.application.port.output.CustomerRestPort;
import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.infrastructure.mapper.CustomerMapper;
import pe.seek.app.customer.infrastructure.proxy.CustomerProxy;
import pe.seek.app.shared.common.rest.RestClientValidator;
import pe.seek.app.shared.exception.CreateCustomerFallBackException;
import pe.seek.app.shared.exception.EntityWrapperException;

import java.util.List;

import static pe.seek.app.shared.constants.GenericErrors.GEN_ALL_02;


@Slf4j
@Component
@RequiredArgsConstructor
class CustomerRestClientAdapter extends RestClientValidator implements CustomerRestPort {

    private final CustomerProxy customerProxy;
    private final CustomerMapper customerMapper;
    private final CustomerCreatedPublisherPort customerCreatedPublisherPort;

    @Override
    @Retry(name = "createCustomerFallBackRetry", fallbackMethod = "fallbackCreateCustomer")
    public Customer createCustomer(Customer customer) throws EntityWrapperException {
        log.info("Creating customer: {}", customer);
        return this.executeCall(
                customerProxy.createCustomer(customerMapper.toCreateDTOFromDomain(customer)),
                customerMapper::toDomainFromResponseDTO
        );
    }

    @Override
    public List<Customer> getAllCustomers() throws EntityWrapperException {
        log.info("Fetching all customers");
        return this.executeCall(
                customerProxy.getAllCustomers(),
                customerMapper::toDomainListFromResponseDTO
        );
    }

    public Customer fallbackCreateCustomer(Customer customer, Exception ex) throws CreateCustomerFallBackException {
        log.warn("Fallback triggered for createCustomer. Sending to publisher. Reason: {}", ex.getMessage());
        customerCreatedPublisherPort.notifyCreatedCustomerFallBack(customer);
        throw new CreateCustomerFallBackException(GEN_ALL_02);
    }
}
