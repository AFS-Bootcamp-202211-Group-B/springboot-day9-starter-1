package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyMongoRepository  extends MongoRepository<Company, String> {
}
