package com.example.Employee.Training.Management.System.Repository;

import com.example.Employee.Training.Management.System.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {

    Roles getByRoleName(String roleName);

}
