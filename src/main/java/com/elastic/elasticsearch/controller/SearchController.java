package com.elastic.elasticsearch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elastic.elasticsearch.model.Employee;
import com.elastic.elasticsearch.repository.EmployeeRepository;

@RestController
@RequestMapping("/rest")
public class SearchController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping(value="/name/{text}")
	public List<Employee> searchName(@PathVariable String text) {
		
		return employeeRepository.findByName(text);
	}
	
	@GetMapping(value="/id/{id}")
	public Optional<Employee> searchId(@PathVariable Long id) {
		
		return employeeRepository.findById(id);
	}
	
	
	@GetMapping(value="/team/{team}")
	public List<Employee> searchTeams(@PathVariable String team) {
		
		return employeeRepository.findByTeam(team);
	}
	
	
	@GetMapping(value="/salary/{salary}")
	public List<Employee> searchSalary(@PathVariable Long salary) {
		
		return employeeRepository.findBySalary(salary);
	}
	
	@GetMapping(value="/search/all")
	public List<Employee> searchAll() {

		List<Employee> empList = new ArrayList<>();
		Iterable<Employee> emp = employeeRepository.findAll();
		emp.forEach(empList::add);
		return empList;
	}
	
	@GetMapping(value="/count")
	public int countAll() {
	
		List<Employee> empList = new ArrayList<>();
		Iterable<Employee> emp = employeeRepository.findAll();
		emp.forEach(empList::add);
		return empList.size();
				//return count;

	}
}
