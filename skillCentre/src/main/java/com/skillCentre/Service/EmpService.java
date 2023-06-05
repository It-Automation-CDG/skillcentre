package com.skillCentre.Service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.skillCentre.Entity.Employee;
import com.skillCentre.Exception.ResourceNotFoundException;
import com.skillCentre.Repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmpService {

    @Autowired
    private EmpRepo empRepo;
    private final JavaMailSender mailSender;
    @Autowired
    private BlobContainerClient blobContainerClient;

    public EmpService(EmpRepo empRepo, JavaMailSender mailSender) {
        this.empRepo = empRepo;
        this.mailSender = mailSender;
    }

    public String addEmp(Employee emp) {
        this.empRepo.save(emp);
        return "Employee Added";
    }

    public List<Employee> getAll()
    {
        List<Employee>employees=this.empRepo.findAll();
        return employees;
    }
    public Employee getFromId(int id)
    {
        Employee employee=this.empRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User" , "id" , id));
        return employee;
    }
    public String delEmp(int id) {
        Employee emp = this.empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        this.empRepo.deleteById(id);
        return "The employee's record has been deleted";
    }

    //this ius to check
    public String updateEmployee(int employeeId, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = empRepo.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmployeeName(updatedEmployee.getEmployeeName());
            employee.setDesignation(updatedEmployee.getDesignation());
            employee.setExperience(updatedEmployee.getExperience());
            employee.setPrimarySkills(updatedEmployee.getPrimarySkills());
            employee.setKnowledgeIn(updatedEmployee.getKnowledgeIn());
            employee.setAdditionalSkills(updatedEmployee.getAdditionalSkills());
            employee.setTimesheet(updatedEmployee.getTimesheet());
            empRepo.save(employee);
            return "Employee updated";
        } else {
            return "Employee not present";
        }
    }

    public String uploadResume(MultipartFile file) throws IOException {

        BlobClient blob = blobContainerClient
                .getBlobClient(file.getOriginalFilename());

        blob.upload(file.getInputStream(), file.getSize(), false);

        String uploadLink = "https://itautomationcdgstorage.blob.core.windows.net/it-auto-cdg/" + file.getOriginalFilename();

        return uploadLink;
    }
    //method to send mail

    public void sendEmail() {
        List<Employee> list = empRepo.findAllEmail();
        for (Employee e:list) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(e.getEmail());
            message.setSubject("congratulations");
            message.setText("congrats for onboarding at incture");

            mailSender.send(message);
        }
    }


}
