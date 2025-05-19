package pe.seek.app.customer.infrastructure.adapter.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerMetricsDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerPredictionDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerRequestDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerResponseDTO;
import pe.seek.app.shared.exception.EntityWrapperException;

import java.util.List;

@RequestMapping("/v1/app-customers")
public interface CustomerApi {

    @PostMapping
    AppCustomerResponseDTO createCustomer(@RequestBody AppCustomerRequestDTO request) throws EntityWrapperException;

    @GetMapping("/metrics")
    CustomerMetricsDTO getCustomerMetrics() throws EntityWrapperException;

    @GetMapping("/predictions")
    List<CustomerPredictionDTO> getCustomerPredictions() throws EntityWrapperException;
}
