package com.udacity.critter.services;

import com.udacity.critter.entity.Employee;
import com.udacity.critter.repository.EmployeesRepository;
import com.udacity.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.time.LocalDate;


@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeesRepository empRepository;


    public Employee getEmployeeById(long empId) {
        return empRepository.getOne(empId);
    }

    public List<Employee> getEmployeesForService(LocalDate localDate, Set<EmployeeSkill> skill) {
        List<Employee> employees = empRepository.getAllByDaysAvailableContains(localDate.getDayOfWeek()).stream().filter(employee -> employee.getSkills().containsAll(skill)).collect(Collectors.toList());
        return employees;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = empRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        empRepository.save(employee);
    }

    public Employee saveEmployee(Employee employee) {
        return empRepository.save(employee);
    }

}
