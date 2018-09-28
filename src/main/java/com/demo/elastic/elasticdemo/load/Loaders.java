package com.demo.elastic.elasticdemo.load;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.elastic.elasticdemo.model.Users;
import com.demo.elastic.elasticdemo.repository.UsersRepository;

@Component
public class Loaders {
	
	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	UsersRepository usersRepository;
	
	@PostConstruct
	@Transactional
	public void loadAll(){
		
		operations.putMapping(Users.class);
		System.out.println("Loading data");
		usersRepository.saveAll(getData());
		System.out.println("Loading completed");
		
	}
	private List<Users> getData() {
		
		List<Users> userses = new ArrayList<>();
		userses.add(new Users("Rakesh",111L,"Ops",1000L));
		userses.add(new Users("Ranjan",222L,"DevOps",2000L));
		userses.add(new Users("Vijay",333L,"Analyst",3000L));
		return userses;
	}
}
