package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMongoRepository extends MongoRepository<Company, String> {
//    List<Employee> findByGender(String gender);
//    List<Employee> findByPage(int page, int pageSize);
}
