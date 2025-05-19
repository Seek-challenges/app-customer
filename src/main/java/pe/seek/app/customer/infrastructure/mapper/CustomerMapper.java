package pe.seek.app.customer.infrastructure.mapper;

import org.mapstruct.Mapper;
import pe.seek.app.customer.domain.Customer;
import pe.seek.app.customer.domain.CustomerMetrics;
import pe.seek.app.customer.domain.CustomerPrediction;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerRequestDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerResponseDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerMetricsDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.CustomerPredictionDTO;
import pe.seek.app.customer.infrastructure.adapter.rest.dto.CustomerRequestDTO;
import pe.seek.app.customer.infrastructure.adapter.rest.dto.CustomerResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequestDTO toCreateDTOFromDomain(Customer customer);
    Customer toDomainFromResponseDTO(CustomerResponseDTO customerResponseDTO);
    List<Customer> toDomainListFromResponseDTO(List<CustomerResponseDTO> customerResponseDTOList);

    AppCustomerResponseDTO toResponseDTOFromDomain(Customer customer);
    Customer toDomainFromRequestDTO(AppCustomerRequestDTO customerRequestDTO);
    CustomerMetricsDTO toMetricsDTOFromDomain(CustomerMetrics customerMetrics);
    CustomerPredictionDTO toPredictionDTOFromDomain(CustomerPrediction customerPrediction);
    List<CustomerPredictionDTO> toPredictionDTOListFromDomain(List<CustomerPrediction> customerPredictions);
}
