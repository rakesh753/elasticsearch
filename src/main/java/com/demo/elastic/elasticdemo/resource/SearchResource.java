package com.demo.elastic.elasticdemo.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.elastic.elasticdemo.model.Users;
import com.demo.elastic.elasticdemo.repository.UsersRepository;

@RestController
@RequestMapping("/rest/search")
public class SearchResource {
	
	@Autowired
	UsersRepository usersRepository;
	
	@GetMapping(value = "/name/{text}")
	public List<Users> searchName(@PathVariable String text){
		
		return usersRepository.findByName(text);
		
	}
	
	@GetMapping(value = "/salary/{salary}")
	public List<Users> searchName(@PathVariable Long salary){
		
		return usersRepository.searchBySalary(salary);
	}
	
	@GetMapping(value = "/all")
	public List<Users> searchAll(){
		
		List<Users> usersList = new ArrayList<>();
		Iterable<Users> userses = usersRepository.findAll();
		/*userses.forEach(users -> {
			usersList.add(users);
		});*/
		userses.forEach(usersList::add);
		return usersList;
	}
}
