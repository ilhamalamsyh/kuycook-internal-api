package com.kuycook.kuycookinternalapi.security.services;

import com.kuycook.kuycookinternalapi.models.Employee;
import com.kuycook.kuycookinternalapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeDetailServiceImpl implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return EmployeeDetailsImpl.build(employee);
    }
}
