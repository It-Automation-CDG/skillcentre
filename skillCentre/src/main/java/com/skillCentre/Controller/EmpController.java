package com.skillCentre.Controller;


import com.skillCentre.Entity.Employee;
import com.skillCentre.Service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/home")

public class EmpController {
    @Autowired
    private EmpService empService;



    @PostMapping("/added")
    public ResponseEntity<String> addedEmp(@RequestBody Employee employee){
        String message = this.empService.addEmp(employee);
        return  new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/uploadResume")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file,
                                               @RequestParam("employeeName") String employeeName,
                                               @RequestParam("designation") String designation,
                                               @RequestParam("experience") String experience,
                                               @RequestParam("primarySkills") String primarySkills,
                                               @RequestParam("knowledgeIn") String knowledgeIn,
                                               @RequestParam("additionalSkills") String additionalSkills) throws IOException {
        this.empService.uploadResume(file, employeeName, designation, experience, primarySkills, knowledgeIn, additionalSkills);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data has been added");
    }

    @PutMapping("/{empId}")
    public ResponseEntity<String> updateEmployee(@PathVariable int employeeId, @RequestBody Employee updatedEmployee) {
        boolean success = empService.updateEmployee(employeeId, updatedEmployee);
        if (success) {
            return ResponseEntity.ok("Employee updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
    }

//    @PutMapping("/update/{userId}")
//
//    public ResponseEntity<String> updateUser(@RequestBody Employee user , @PathVariable ("userId") int id){
//        String message = this.empService.update(id,user);
//        return  new ResponseEntity<>(message, HttpStatus.OK);
//    }

    @GetMapping("/getId")
    public ResponseEntity<List>getAll()
    {
        List<Employee> employees=this.empService.getAll();
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @DeleteMapping("/delEmp/{empId}")
    public ResponseEntity<String> delEmp(@PathVariable("empId") int id) {
        String msg = this.empService.delEmp(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @Scheduled(cron = "0 07 11 * * *") // Schedule the task to run at 6 PM daily
    @PostMapping("sendEmail")
public ResponseEntity<String> sendEmail(){
     this.empService.sendEmail();
    return  new ResponseEntity<>("email is sent to all employee", HttpStatus.OK);
}

}
