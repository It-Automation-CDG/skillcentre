package com.skillCentre.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor


public class Employee {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int EmployeeId;

    private String EmployeeName;
    private String Designation;
    private String Experience;
    private String PrimarySkills;
    private String KnowledgeIn;
    private String AdditionalSkills;
    private String email;
    private String resumeLink;
    private boolean timesheet;

}
