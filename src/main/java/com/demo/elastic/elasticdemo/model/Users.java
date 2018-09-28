package com.demo.elastic.elasticdemo.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="users",type="users",shards=1)
public class Users {
	
	public Users() {
	}

	public Users(String name, Long id, String teamName, Long salary) {
		this.name = name;
		this.id = id;
		this.teamName = teamName;
		Salary = salary;
	}
	private String name;
	private Long id;
	private String teamName;
	private Long Salary;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getSalary() {
		return Salary;
	}

	public void setSalary(Long salary) {
		Salary = salary;
	}

}
