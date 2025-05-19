package pe.seek.app.customer;

import org.springframework.boot.SpringApplication;
import pe.seek.app.MainApplication;

public class TestCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.from(MainApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
