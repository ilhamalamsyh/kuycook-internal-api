package com.kuycook.kuycookinternalapi.repositories;

import com.kuycook.kuycookinternalapi.models.Role;
import com.kuycook.kuycookinternalapi.utils.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
