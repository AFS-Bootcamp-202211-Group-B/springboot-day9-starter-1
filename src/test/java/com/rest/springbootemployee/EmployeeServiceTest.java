package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeMongoRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeMongoRepository employeeMongoRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 10000);
        employees.add(employee);

        when(employeeMongoRepository.findAll()).thenReturn(employees);

        //when
        List<Employee> result = employeeService.findAll();

        //then
        assertThat(result, hasSize(1));
        assertThat(result.get(0), equalTo(employee));
        verify(employeeMongoRepository).findAll();

    }

    @Test
    void should_update_only_age_and_salary_when_update_all_given_employees() {
        //given
        int employeeId = 1;
        Employee employee = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 10000);
        Employee toUpdateEmployee = new Employee(new ObjectId().toString(), "Tom", 23, "Male", 12000);

        when(employeeRepository.findById(employeeId)).thenReturn(employee);

        //when
        Employee updatedEmployee = employeeService.update(employeeId, toUpdateEmployee);

        //then
        verify(employeeRepository).findById(employeeId);
        assertThat(updatedEmployee.getAge(), equalTo(23));
        assertThat(updatedEmployee.getSalary(), equalTo(12000));
        assertThat(updatedEmployee.getName(), equalTo("Susan"));
        assertThat(updatedEmployee.getGender(), equalTo("Female"));

    }

    @Test
    void should_return_employee_when_find_by_id_given_employee() {
        // given
        Integer employeeId = 1;
        Employee employee = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 7000);
        given(employeeRepository.findById(employeeId)).willReturn(employee);

        // when
        Employee result = employeeService.findById(employeeId);

        // should
        verify(employeeRepository).findById(employeeId);
        assertThat(result, equalTo(employee));
    }

    @Test
    void should_return_employees_when_find_by_gender_given_employees() {
        // given
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 7000);
        Employee employee2 = new Employee(new ObjectId().toString(), "Lisa", 20, "Female", 7000);
        Employee employee3 = new Employee(new ObjectId().toString(), "Jim", 21, "Male", 7000);

        String gender = "Female";
        given(employeeRepository.findByGender(gender)).willReturn(employees);

        // when
        List<Employee> result = employeeService.findByGender(gender);

        // should
        verify(employeeRepository).findByGender(gender);
        assertThat(result, equalTo(employees));
    }

    @Test
    void should_return_employees_when_find_by_page_given_employees() {
        // given
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 7000);
        Employee employee2 = new Employee(new ObjectId().toString(), "Lisa", 20, "Female", 7000);

        int page = 1;
        int pageSize = 2;
        given(employeeRepository.findByPage(1, 2)).willReturn(employees);

        // when
        List<Employee> result = employeeService.findByPage(page, pageSize);

        // should
        verify(employeeRepository).findByPage(page, pageSize);
        assertThat(result, equalTo(employees));
    }

    @Test
    void should_call_delete_with_specific_id_when_delete_given_an_id() {
        // given
        Integer employeeId = 1;

        // when
        employeeService.delete(employeeId);

        // should
        verify(employeeRepository).delete(employeeId);
    }

    @Test
    void should_call_create_with_specific_employee_when_create_given_an_employee() {
        // given
        Employee employee = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 7000);
        Employee createdEmployee = new Employee(new ObjectId().toString(), "Susan", 22, "Female", 7000);

        given(employeeRepository.create(employee)).willReturn(createdEmployee);

        // when
        Employee result = employeeService.create(employee);

        // should
        verify(employeeRepository).create(employee);
        assertThat(result, equalTo(createdEmployee));
    }
}
