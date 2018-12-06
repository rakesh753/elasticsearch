package com.elastic.elasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="employee",type="users", shards = 1)
public class Employee {
	
	private long id;
	private String name;
	private String team;
	private long salary;
	
	public Employee() {
		
	}
	
	public Employee(long id, String name, String team, long salary) {
		
		this.id = id;
		this.name = name;
		this.team = team;
		this.salary = salary;
	}

	public long getid() {
		return id;
	}

	public void setid(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

}
