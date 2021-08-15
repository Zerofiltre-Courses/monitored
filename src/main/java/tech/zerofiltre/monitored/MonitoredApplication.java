package tech.zerofiltre.monitored;

import java.security.SecureRandom;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.zerofiltre.monitored.model.Employee;
import tech.zerofiltre.monitored.repository.EmployeeRepository;

@SpringBootApplication
public class MonitoredApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoredApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(EmployeeRepository employeeRepository){
		return args -> {
			employeeRepository.save(new Employee("Abou","bakar",2000));
			employeeRepository.save(new Employee("Ali","Abdoul",4000));
			employeeRepository.save(new Employee("Roy","KONE",3000));
			employeeRepository.save(new Employee("Nick","SIMON",10000));
		};

	}

}
