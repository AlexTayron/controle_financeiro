package com.alextayron.schedule_finance.employee.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alextayron.schedule_finance.employee.entity.Employee;
import com.alextayron.schedule_finance.employee.service.EmployeeService;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    private EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        var employeeId = employeeService.createEmployee(createEmployeeDto);
        return ResponseEntity.created(URI.create("/v1/employees/" + employeeId.toString())).build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") String employeeId) {
        var employee = employeeService.getEmployeeById(employeeId);
       
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();            
        }

    }

    @GetMapping
    public ResponseEntity<List<Employee>> listEmployees(){
        var employees = employeeService.listEmployees();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Void> updateEmployeeById(@PathVariable("employeeId") String employeeId, @RequestBody UpdateEmployeeDto updateEmployeeDto){
        employeeService.updateEmployeeById(employeeId, updateEmployeeDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteById(@PathVariable("employeeId") String employeeId) {
        employeeService.deleteById(employeeId);
        return ResponseEntity.noContent().build();
    }
}
