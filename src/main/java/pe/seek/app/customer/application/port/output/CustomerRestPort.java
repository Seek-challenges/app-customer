package pe.seek.app.customer.application.port.output;

import pe.seek.app.customer.domain.Customer;
import pe.seek.app.shared.exception.EntityWrapperException;

import java.util.List;

public interface CustomerRestPort {
    Customer createCustomer(Customer customer) throws EntityWrapperException;
    List<Customer> getAllCustomers() throws EntityWrapperException;
}
