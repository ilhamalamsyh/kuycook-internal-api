package com.kuycook.kuycookinternalapi.repositories;

import com.kuycook.kuycookinternalapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAuthRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmail(String email);
}
