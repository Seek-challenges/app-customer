package pe.seek.app.customer.infrastructure.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pe.seek.app.customer.application.port.input.CustomerServicePort;
import pe.seek.app.customer.domain.CustomerMetrics;
import pe.seek.app.customer.domain.CustomerPrediction;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.AppCustomerRequestDTO;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "APP_ENVIRONMENT=dev"
        }
)
public class CustomerControllerTest {


        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private CustomerServicePort customerServicePort;

        @Container
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");

        @Container
        static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

        @DynamicPropertySource
        static void setProps(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", mysql::getJdbcUrl);
                registry.add("spring.datasource.username", mysql::getUsername);
                registry.add("spring.datasource.password", mysql::getPassword);
                registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
                registry.add("spring.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        }

        @Test
        void shouldCreateCustomer() throws Exception {
                AppCustomerRequestDTO request = new AppCustomerRequestDTO(
                        "Arturo", "Diaz", "30", "321321321", LocalDate.of(1995, 1, 1), "password123"
                );

                TokenDTO token = new TokenDTO("mocked-token");
                Mockito.when(customerServicePort.createCustomer(Mockito.any(), Mockito.anyString()))
                        .thenReturn(token);

                mockMvc.perform(post("/v1/app-customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token").value("mocked-token"));
        }

        @Test
        @WithMockUser(username = "mock-user", roles = {"USER"})
        void shouldReturnCustomerMetrics() throws Exception {
                CustomerMetrics metrics = CustomerMetrics.builder()
                        .averageAge(25.0)
                        .ageStandardDeviation(4.5)
                        .build();

                Mockito.when(customerServicePort.getCustomerMetrics())
                        .thenReturn(metrics);

                mockMvc.perform(get("/v1/app-customers/metrics"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.averageAge").value(25.0));
        }

        @Test
        @WithMockUser(username = "mock-user", roles = {"USER"})
        void shouldReturnCustomerPredictions() throws Exception {
                List<CustomerPrediction> predictions = List.of(
                        CustomerPrediction.builder()
                                .firstName("Arturo")
                                .lastName("Diaz")
                                .age("30")
                                .birthDate(LocalDate.of(1995, 1, 1))
                                .build()
                );
                Mockito.when(customerServicePort.getCustomerPrediction())
                        .thenReturn(predictions);

                mockMvc.perform(get("/v1/app-customers/predictions"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].firstName").value("Arturo"));
        }
}
