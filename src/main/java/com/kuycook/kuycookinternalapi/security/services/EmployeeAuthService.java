package com.kuycook.kuycookinternalapi.security.services;

import com.kuycook.kuycookinternalapi.dto.response.CurrentUserResponse;
import com.kuycook.kuycookinternalapi.models.Employee;
import com.kuycook.kuycookinternalapi.repositories.EmployeeAuthRepository;
import com.kuycook.kuycookinternalapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAuthService implements UserDetailsService {

    @Autowired
    EmployeeAuthRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null)
            throw new UsernameNotFoundException("User not found");
        return new CurrentUserResponse(employee);
    }
}
