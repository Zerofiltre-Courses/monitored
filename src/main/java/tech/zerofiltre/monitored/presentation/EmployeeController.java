package tech.zerofiltre.monitored.presentation;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.zerofiltre.monitored.model.Employee;
import tech.zerofiltre.monitored.presentation.error.ResourceNotFoundException;
import tech.zerofiltre.monitored.service.EmployeeService;
import tech.zerofiltre.monitored.service.OOMGenerator;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  private EmployeeService employeeService;
  private OOMGenerator oomGenerator;
  private MeterRegistry meterRegistry;


  public EmployeeController(EmployeeService employeeService,      OOMGenerator oomGenerator, MeterRegistry meterRegistry) {
    this.employeeService = employeeService;
    this.oomGenerator = oomGenerator;
    this.meterRegistry = meterRegistry;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Employee hire(@RequestBody Employee employee) {
    meterRegistry.counter("employee","operation","hire").increment();
    return employeeService.hire(employee);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/{id}")
  public Employee update(@RequestBody Employee employee, @PathVariable("id") long id) {
    meterRegistry.counter("employee","operation","update").increment();
    return employeeService.update(id, employee);
  }


  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Employee get(@PathVariable("id") long id) {
    meterRegistry.counter("employee","operation","get").increment();
    return employeeService.get(id);

  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void fire(@PathVariable("id") long id) {
    meterRegistry.counter("employee","operation","fire").increment();
    employeeService.fire(id);
  }


  @GetMapping("/log")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void startLogging() {
    employeeService.startLogging();
  }

  @GetMapping("/oom/{timeout}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void startOOM(@PathVariable("timeout") long timeout) {
    oomGenerator.startOOMFor(timeout);
  }


}
