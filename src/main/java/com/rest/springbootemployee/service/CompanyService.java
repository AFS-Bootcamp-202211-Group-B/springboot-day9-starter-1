package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.exception.NoEmployeeFoundException;
import com.rest.springbootemployee.repository.CompanyMongoRepository;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeMongoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    private CompanyMongoRepository companyMongoRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyMongoRepository companyMongoRepository ) {
        this.companyRepository = companyRepository;
        this.companyMongoRepository = companyMongoRepository;
    }

    public List<Company> findAll() {
        return companyMongoRepository.findAll();
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyMongoRepository.findAll(PageRequest.of(page,pageSize))
                .toList();

    }

    public Company findById(String companyId) {
        return companyMongoRepository.findById(companyId).orElseThrow(NoEmployeeFoundException::new);
    }

    public Company create(Company company) {
        return companyMongoRepository.save(company);
    }

    public void delete(String companyId) {
        companyMongoRepository.deleteById(companyId);;
    }

    public Company update(String companyId, Company toUpdateCompany) {
        Company existingCompany = companyRepository.findById(companyId);
        if (toUpdateCompany.getName() != null) {
            existingCompany.setName(toUpdateCompany.getName());
        }
        return existingCompany;
    }

    public List<Employee> getEmployees(String companyId) {
        Company company = companyRepository.findById(companyId);
        return company.getEmployees();
    }

}
