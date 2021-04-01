package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByUsers_UserId(Long userId);

}
