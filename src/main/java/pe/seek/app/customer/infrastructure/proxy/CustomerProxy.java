package pe.seek.app.customer.infrastructure.proxy;

import pe.seek.app.customer.infrastructure.adapter.rest.dto.CustomerRequestDTO;
import pe.seek.app.customer.infrastructure.adapter.rest.dto.CustomerResponseDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CustomerProxy {

    @POST("/v1/customers")
    Call<CustomerResponseDTO> createCustomer(
            @Body CustomerRequestDTO customerRequestDTO
    );

    @GET("/v1/customers/{phone}")
    Call<CustomerResponseDTO> getCustomerByPhone(
            @Path("phone") String phone
    );

    @GET("/v1/customers/all")
    Call<List<CustomerResponseDTO>> getAllCustomers();

    @PUT("/v1/customers/update/{phone}")
    Call<CustomerResponseDTO> updateCustomer(
            @Path("phone") String phone,
            @Body CustomerRequestDTO customerRequestDTO
    );

    @DELETE("/v1/customers/{phone}")
    Call<Void> deleteCustomer(
            @Path("phone") String phone
    );
}
