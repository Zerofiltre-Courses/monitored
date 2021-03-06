package tech.zerofiltre.monitored.service;

import org.springframework.stereotype.Service;
import tech.zerofiltre.monitored.model.Employee;
import tech.zerofiltre.monitored.repository.EmployeeRepository;

@Service
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
}
