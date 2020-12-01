package com.udacity.critter.services;


import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.repository.EmployeesRepository;
import com.udacity.critter.repository.PetsRepository;
import com.udacity.critter.repository.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.udacity.critter.entity.Schedule;
import com.udacity.critter.repository.CustomersRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchedulesService {

    @Autowired
    private SchedulesRepository scheduleRepository;

    @Autowired
    private EmployeesRepository employeeRepository;
    @Autowired
    private PetsRepository petRepository;


    @Autowired
    private CustomersRepository custRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesForCustomer(long custId) {
        Customer cust = custRepository.getOne(custId);
        return  scheduleRepository.getAllByPetsIn(cust.getPets());
    }

    public List<Schedule> getAllSchedulesForEmployee(long empId) {
        Employee emp = employeeRepository.getOne(empId);
        return scheduleRepository.getAllByEmployeesContains(emp);
    }

    public List<Schedule> getAllSchedulesForPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.getAllByPetsContains(pet);
    }



    public Schedule saveSchedule(Schedule schedule, List<Long> empId, List<Long> pId) {
        List<Employee> emp = employeeRepository.findAllById(empId);
        List<Pet> pet = petRepository.findAllById(pId);
        schedule.setPets(pet);
        schedule.setEmployees(emp);
        return scheduleRepository.save(schedule);
    }
}
