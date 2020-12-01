package com.udacity.critter.schedule;

import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.entity.Schedule;
import com.udacity.critter.services.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {


    @Autowired
    private SchedulesService scheduleService;



    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule sch = new Schedule();
        sch.setDate(scheduleDTO.getDate());
        sch.setActivities(scheduleDTO.getActivities());
        return getScheduleDTO(scheduleService.saveSchedule(sch, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }


    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schList = scheduleService.getAllSchedules();
        return schList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }


    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schList = scheduleService.getAllSchedulesForPet(petId);
        return schList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schList = scheduleService.getAllSchedulesForEmployee(employeeId);
        return schList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schList = scheduleService.getAllSchedulesForCustomer(customerId);
        return schList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }



    private ScheduleDTO getScheduleDTO(Schedule sch) {
        ScheduleDTO sDTO = new ScheduleDTO();
        sDTO.setId(sch.getId());
        sDTO.setEmployeeIds(sch.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        sDTO.setPetIds(sch.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        sDTO.setDate(sch.getDate());
        sDTO.setActivities(sch.getActivities());
        return sDTO;
    }
}
