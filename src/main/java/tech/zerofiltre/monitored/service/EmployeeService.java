package tech.zerofiltre.monitored.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.zerofiltre.monitored.model.Employee;
import tech.zerofiltre.monitored.presentation.error.ResourceNotFoundException;
import tech.zerofiltre.monitored.repository.EmployeeRepository;

@Service
@Slf4j
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }


  public Employee hire(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Employee update(long id, Employee employee) {
    employee.setRegistrationId(id);
    return employeeRepository.save(employee);
  }

  public Employee get(long id) {
    return employeeRepository.getById(id);

  }

  public void fire(long id) {
    Employee employee = get(id);
    employeeRepository.delete(employee);

  }

  public void startLogging() {
    new Thread(() -> {
      while (true) {
        log.debug("Logging with the DEBUG level");
        log.info("Logging with the INFO level");
        try {
          Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "infinite-logger").start();

  }
}
