package pe.seek.app.customer.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import pe.seek.app.customer.application.port.input.AuthServicePort;
import pe.seek.app.customer.application.port.input.CustomerServicePort;
import pe.seek.app.customer.application.port.output.CustomerRestPort;
import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.domain.CustomerMetrics;
import pe.seek.app.customer.domain.CustomerPrediction;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.shared.exception.EntityWrapperException;
import pe.seek.app.shared.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static pe.seek.app.shared.constants.AsyncConstants.ASYNC_VIRTUAL_THREAD_TASK_EXECUTOR;

@Slf4j
@Service
class CustomerService implements CustomerServicePort {

    private final AuthServicePort authService;
    private final AsyncTaskExecutor executorService;
    private final CustomerRestPort customerRestPort;

    CustomerService(AuthServicePort authService, @Qualifier(ASYNC_VIRTUAL_THREAD_TASK_EXECUTOR) AsyncTaskExecutor taskExecutor, CustomerRestPort customerRestPort) {
        this.authService = authService;
        this.executorService = taskExecutor;
        this.customerRestPort = customerRestPort;
    }


    @Override
    public TokenDTO createCustomer(Customer customer, String password) throws EntityWrapperException, UserNotFoundException {
        customerRestPort.createCustomer(customer);
        return authService.register(customer.getPhone(), password);
    }

    @Override
    public CustomerMetrics getCustomerMetrics() throws EntityWrapperException {
        List<Customer> customers = customerRestPort.getAllCustomers();

        List<Integer> ages = customers.stream()
                .map(c -> Integer.parseInt(c.getAge()))
                .toList();

        double avgAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0);


        CompletableFuture<Double> futureStdDevAge = executorService.submitCompletable(() -> calculateStandardDeviation(ages, avgAge));
        CompletableFuture<Double> futureAvgBirthDate = executorService.submitCompletable(() -> customers.stream()
                .mapToLong(c -> c.getBirthDate().toEpochDay())
                .average().orElse(0));

        CompletableFuture.allOf(futureStdDevAge, futureAvgBirthDate).join();

        return CustomerMetrics.builder()
                .averageAge(avgAge)
                .averageBirthDate(futureAvgBirthDate.join())
                .ageStandardDeviation(futureStdDevAge.join())
                .build();
    }

    @Override
    public List<CustomerPrediction> getCustomerPrediction() throws EntityWrapperException {
        List<Customer> customers = customerRestPort.getAllCustomers();
        return customers.stream()
                .map(customer -> {
                    try {
                        double remainingYears = Math.max(80 - Integer.parseInt(customer.getAge()), 0);
                        LocalDate predictedDeathDate = customer.getBirthDate().plusYears(80);

                        return (CustomerPrediction) CustomerPrediction.builder()
                                .firstName(customer.getFirstName())
                                .lastName(customer.getLastName())
                                .phone(customer.getPhone())
                                .age(customer.getAge())
                                .birthDate(customer.getBirthDate())
                                .lifeExpectancy(remainingYears)
                                .predictedDeathDate(predictedDeathDate)
                                .build();
                    } catch (NumberFormatException e) {
                        log.warn("Edad inválida para cliente {}: {}", customer.getPhone(), customer.getAge());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private double calculateStandardDeviation(List<Integer> values, double mean) {
        double variance = values.stream()
                .mapToDouble(age -> Math.pow(age - mean, 2))
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }
}
