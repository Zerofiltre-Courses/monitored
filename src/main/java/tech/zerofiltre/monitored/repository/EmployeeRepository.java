package tech.zerofiltre.monitored.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.zerofiltre.monitored.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
