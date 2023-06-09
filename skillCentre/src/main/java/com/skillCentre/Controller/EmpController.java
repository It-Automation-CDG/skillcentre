package com.skillCentre.Controller;


import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
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

    @Autowired
    private BlobContainerClient blobContainerClient;

    @PostMapping("/added")
    public ResponseEntity<String> addedEmp(@RequestBody Employee employee){
        String message = this.empService.addEmp(employee);
        return  new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/getpdf")
    public void getPdf(){
        PagedIterable<BlobItem> items = blobContainerClient.listBlobs();
        for (BlobItem blob : items) {
            System.out.println(blob.getName());
        }
    }

    @PostMapping("/uploadResume/{empId}")
    public String uploadResume(@RequestParam("file") MultipartFile file, @PathVariable("empId") int empId) throws IOException {
        return this.empService.uploadResume(file);
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<String> updateUser(@RequestBody Employee user , @PathVariable ("userId") int id){
        String message = this.empService.updateEmployee(id,user);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

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
