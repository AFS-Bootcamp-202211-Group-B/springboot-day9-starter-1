package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoEmployeeFoundException;
import com.rest.springbootemployee.repository.EmployeeMongoRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {// SUT

    private EmployeeRepository employeeRepository; // DOC
    private EmployeeMongoRepository employeeMongoRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMongoRepository employeeMongoRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMongoRepository = employeeMongoRepository;
    }

    // 1. verify interaction
        // when EmployeeService.findAll is called, it will call employeeRepository.findAll()
    // 2. verify data
        // return the data get from employeeRepository.findAll() without any change.
    public List<Employee> findAll() {
//        return employeeRepository.findAll();
        return employeeMongoRepository.findAll();
    }

    // 1. verify interaction
        // when EmployeeService.update is called, it will call employeeRepository.findById(id)
    // 2. verify data
        // when input an employee, only the age and salary will be changed, name and gender will not change.
    public Employee update(String id, Employee employee) {
        Employee existingEmployee = employeeMongoRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
//        Employee existingEmployee = optionalExistingEmployee.get();
        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        employeeMongoRepository.save(existingEmployee);
        return existingEmployee;

    }


    public Employee findById(Integer id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> findByPage(int page, int pageSize) {
        return employeeMongoRepository.findAll(PageRequest.of(page-1,pageSize)).toList();
    }

    public void delete(String id) {
        employeeMongoRepository.deleteById(String.valueOf(id));
    }

    public Employee create(Employee employee) {
//        return employeeRepository.create(employee);
        return employeeMongoRepository.save(employee);
    }
}
