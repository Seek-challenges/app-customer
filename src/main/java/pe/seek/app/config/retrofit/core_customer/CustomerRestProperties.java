package pe.seek.app.config.retrofit.core_customer;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "services-api.core.customer")
class CustomerRestProperties {
    private final String baseUrl;
    private final long connectTimeout;
    private final long readTimeout;
    private final long writeTimeout;
    private final int maxRequest;

    @ConstructorBinding
    public CustomerRestProperties(String baseUrl, long connectTimeout, long readTimeout, long writeTimeout, int maxRequest) {
        this.baseUrl = baseUrl;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.maxRequest = maxRequest;
    }
}
