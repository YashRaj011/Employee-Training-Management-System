package com.example.Employee.Training.Management.System.Repository;

import com.example.Employee.Training.Management.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}

