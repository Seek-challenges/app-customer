package pe.seek.app.shared.common.rest;

import com.google.gson.JsonSyntaxException;
import org.springframework.web.client.RestClientException;
import pe.seek.app.shared.exception.EntityWrapperException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.function.Function;

public abstract class RestClientValidator {

    public <T> T executeCall(final Call<T> calls) throws EntityWrapperException {
        Response<T> response = callResponse(calls);
        return this.responseValidator(response);
    }

    public <T, R> R executeCall(final Call<T> calls, final Function<T, R> functionParser) throws EntityWrapperException {
        Response<T> response = callResponse(calls);
        this.responseValidator(response);
        return functionParser.apply(response.body());
    }

    private <T> Response<T> callResponse(Call<T> call) {
        try {
            return call.execute();
        } catch (JsonSyntaxException var3) {
            throw new RestClientException("Unable to decode into json", var3);
        } catch (SocketTimeoutException | ConnectException | UnknownHostException var4) {
            throw new RestClientException("Unable connect to host", var4);
        } catch (IOException var5) {
            throw new RestClientException("General Exception", var5);
        }
    }

    private <T> T responseValidator(Response<T> response) throws EntityWrapperException {
        if (!response.isSuccessful()) {
            int statusCode = response.code();
            String errorBody = Optional.ofNullable(response.errorBody())
                    .map(responseBody -> {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .orElse("");
            throw new EntityWrapperException(statusCode, errorBody);
        }
        return response.body();
    }
}
