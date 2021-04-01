package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByUsers_UserId(Long userId);

}
