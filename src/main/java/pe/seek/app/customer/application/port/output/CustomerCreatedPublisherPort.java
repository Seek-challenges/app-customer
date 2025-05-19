package pe.seek.app.customer.application.port.output;

import pe.seek.app.customer.domain.Customer;

public interface CustomerCreatedPublisherPort {
    void notifyCreatedCustomerFallBack(Customer customer);
}
