package pe.seek.app.customer.application.port.input;

import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.domain.CustomerMetrics;
import pe.seek.app.customer.domain.CustomerPrediction;
import pe.seek.app.shared.exception.EntityWrapperException;

import java.util.List;

public interface CustomerServicePort {
    Customer createCustomer(Customer customer) throws EntityWrapperException;
    CustomerMetrics getCustomerMetrics() throws EntityWrapperException;
    List<CustomerPrediction> getCustomerPrediction() throws EntityWrapperException;
}
