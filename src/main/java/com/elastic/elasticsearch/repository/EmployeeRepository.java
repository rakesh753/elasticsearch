package com.elastic.elasticsearch.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.elastic.elasticsearch.model.Employee;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long>{

	List<Employee> findByName(String name);
	Optional<Employee> findById(Long id);
	List<Employee> findByTeam(String team);
	List<Employee> findBySalary(Long salary);

}
