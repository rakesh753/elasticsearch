package com.elastic.elasticsearch.loaders;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.elastic.elasticsearch.model.Employee;
import com.elastic.elasticsearch.repository.EmployeeRepository;

@Component
public class Loaders {

	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostConstruct
	@Transactional
	public void loadAll() {
		
		operations.putMapping(Employee.class);
		System.out.println("Loading !!!");
		employeeRepository.saveAll(getData());
		System.out.println("Loading complete !!!");
		
	}
	
	public List<Employee> getData() {
		
		List<Employee> emp = new ArrayList<>();
		emp.add(new Employee(111L,"Rakesh","QA",1000l));
		emp.add(new Employee(222L,"Ranjan","Ops",2000l));
		emp.add(new Employee(333L,"Funtoos","Dev",3000l));
		emp.add(new Employee(444L,"Professor","Analyst",4000l));
		emp.add(new Employee(555L,"Scientist","Research",5000l));
		return emp;
		
	}
}
