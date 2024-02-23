package com.example.Employee.Training.Management.System.Repository;

import com.example.Employee.Training.Management.System.model.UserCompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<UserCompanyDetails, Integer> {

}
