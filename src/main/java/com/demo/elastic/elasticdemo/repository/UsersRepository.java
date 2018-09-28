package com.demo.elastic.elasticdemo.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.demo.elastic.elasticdemo.model.Users;

public interface UsersRepository extends ElasticsearchRepository<Users, Long>{

	List<Users> findByName(String text);
	List<Users> searchBySalary(Long salary);
	

}
