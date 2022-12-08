package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoCompanyFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        ArrayList<Employee> employeesOfCompany1 = new ArrayList<>();
        employeesOfCompany1.add(new Employee(new ObjectId().toString(), "Carlos", 26, "Male", 70000));
        employeesOfCompany1.add(new Employee(new ObjectId().toString(), "Nicole", 22, "Female", 80000));
        companies.add(new Company( "spring", employeesOfCompany1));

        ArrayList<Employee> employeesOfCompany2 = new ArrayList<>();
        employeesOfCompany2.add(new Employee(new ObjectId().toString(), "Alice", 21, "Female", 90000));
        employeesOfCompany2.add(new Employee(new ObjectId().toString(), "Bob", 20, "Male", 80000));
        companies.add(new Company("summer", employeesOfCompany2));

        ArrayList<Employee> employeesOfCompany3 = new ArrayList<>();
        employeesOfCompany3.add(new Employee(new ObjectId().toString(), "Zoe", 23, "Female", 85000));
        employeesOfCompany3.add(new Employee(new ObjectId().toString(), "Thomas", 22, "Male", 83000));
        companies.add(new Company( "autumn", employeesOfCompany3));
    }

    public List<Company> findAll() {
        return companies;
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company findById(String id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public void delete(String id) {
        Company existingCompany = findById(id);
        companies.remove(existingCompany);
    }

    public List<Employee> getEmployees(String id) {
        return findById(id).getEmployees();
    }

    public Company create(Company company) {
        String nextId = generateNextId();
        company.setId(nextId);
        companies.add(company);
        return company;
    }

    private String generateNextId() {
        int maxId = companies.stream()
                .mapToInt(company -> Integer.parseInt(company.getId()))
                .max()
                .orElse(1);
        return String.valueOf(maxId + 1);
    }

    public Company update(String id, Company company) {
        Company existingCompany = findById(id);
        if (company.getName() != null) {
            existingCompany.setName(company.getName());
        }
        return existingCompany;
    }

    public void clearAll() {
        companies.clear();
    }
}
