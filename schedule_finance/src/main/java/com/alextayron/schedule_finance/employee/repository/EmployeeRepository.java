package com.alextayron.schedule_finance.employee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alextayron.schedule_finance.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

}
