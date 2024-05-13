package com.alextayron.schedule_finance.employee.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alextayron.schedule_finance.employee.repository.EmployeeRepository;
import com.alextayron.schedule_finance.employee.controller.CreateEmployeeDto;
import com.alextayron.schedule_finance.employee.controller.UpdateEmployeeDto;
import com.alextayron.schedule_finance.employee.entity.Employee;;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public UUID createEmployee(CreateEmployeeDto createEmployeeDto) {
        var entity = new Employee(
                UUID.randomUUID(),
                createEmployeeDto.name(),
                createEmployeeDto.email(),
                createEmployeeDto.phone(),
                createEmployeeDto.password(),
                Instant.now(),
                null);

        var employeeSaved = employeeRepository.save(entity);

        return employeeSaved.getEmployeeId();
    }

    public Optional<Employee> getEmployeeById(String employeeId) {

        return employeeRepository.findById(UUID.fromString(employeeId));

    }

    public List<Employee> listEmployees() {
        return employeeRepository.findAll();
    }

    public void updateEmployeeById(String employeeId, UpdateEmployeeDto updateEmployeeDto) {
        var id = UUID.fromString(employeeId);
        var employeeEntity = employeeRepository.findById(id);

        if (employeeEntity.isPresent()) {
            var employee = employeeEntity.get();

            if (updateEmployeeDto.name() != null) {
                employee.setEmployeename(updateEmployeeDto.name());
            }
            if (updateEmployeeDto.phone() != null) {
                employee.setPhone(updateEmployeeDto.phone());
            }
            if (updateEmployeeDto.password() != null) {
                employee.setPassword(updateEmployeeDto.password());
            }

            employeeRepository.save(employee);
        }
    }

    public void deleteById(String employeeId) {
        var id = UUID.fromString(employeeId);
        var employeeExistent = employeeRepository.existsById(id);

        if (employeeExistent) {
            employeeRepository.deleteById(id);
        }
    }
}
