package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoEmployeeFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    List<Employee> employees;

    public EmployeeRepository() {
        employees = new ArrayList<>();
        employees.add(new Employee(new ObjectId().toString(), "Lily", 20, "Female", 80000));
        employees.add(new Employee(new ObjectId().toString(), "Desiree", 20, "Female", 80000));
        employees.add(new Employee(new ObjectId().toString(), "Rafael", 20, "Male", 80000));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(Integer id) {
        return employees.stream()
                .filter(employee -> ((Integer) Integer.parseInt(employee.getId())).equals(id))
                .findFirst()
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        Integer nextId = generateNextId();
        //        this.id = id;
        employee.setId(String.valueOf(nextId));
        employees.add(employee);
        return employee;
    }

    private Integer generateNextId() {
        int maxId = employees.stream()
                .mapToInt(employee -> Integer.parseInt(employee.getId()))
                .max()
                .orElse(1);
        return maxId + 1;
    }

//    requirement: update age and salary

    public void delete(Integer id) {
        Employee existingEmployee = findById(id);
        employees.remove(existingEmployee);
    }

    public List<Employee> findByPage(int page, int pageSize) {
        return employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void clearAll() {
        employees.clear();
    }
}

//{
//        "id": 5,
//        "name": "Lily",
//        "age": 20,
//        "gender": "Female",
//        "salary": 8000
//        }
