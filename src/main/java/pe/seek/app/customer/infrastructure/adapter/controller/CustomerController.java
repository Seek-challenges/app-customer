package pe.seek.app.customer.infrastructure.adapter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pe.seek.app.customer.application.port.input.CustomerServicePort;
import pe.seek.app.customer.infrastructure.adapter.controller.api.CustomerApi;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerRequestDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerResponseDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerMetricsDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerPredictionDTO;
import pe.seek.app.customer.infrastructure.mapper.CustomerMapper;
import pe.seek.app.shared.exception.EntityWrapperException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class CustomerController implements CustomerApi {

    private final CustomerMapper customerMapper;
    private final CustomerServicePort customerService;

    @Override
    public AppCustomerResponseDTO createCustomer(AppCustomerRequestDTO request) throws EntityWrapperException {
        return customerMapper.toResponseDTOFromDomain(
                customerService.createCustomer(
                        customerMapper.toDomainFromRequestDTO(request)
                )
        );
    }

    @Override
    public CustomerMetricsDTO getCustomerMetrics() throws EntityWrapperException {
        return customerMapper.toMetricsDTOFromDomain(
                customerService.getCustomerMetrics()
        );
    }

    @Override
    public List<CustomerPredictionDTO> getCustomerPredictions() throws EntityWrapperException {
        return customerMapper.toPredictionDTOListFromDomain(
                customerService.getCustomerPrediction()
        );
    }
}
