package pe.seek.app.customer.application.port.input;

import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.domain.CustomerMetrics;
import pe.seek.app.customer.domain.CustomerPrediction;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.shared.exception.EntityWrapperException;
import pe.seek.app.shared.exception.UserNotFoundException;

import java.util.List;

public interface CustomerServicePort {
    TokenDTO createCustomer(Customer customer, String password) throws EntityWrapperException, UserNotFoundException;
    CustomerMetrics getCustomerMetrics() throws EntityWrapperException;
    List<CustomerPrediction> getCustomerPrediction() throws EntityWrapperException;
}
