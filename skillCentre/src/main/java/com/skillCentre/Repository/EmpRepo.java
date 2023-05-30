package com.skillCentre.Repository;

import com.skillCentre.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmpRepo extends JpaRepository<Employee , Integer> {
    @Query(value = "select * FROM employee ",nativeQuery=true)
    public List<Employee> findAllEmail();
}
