package com.emp1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emp1.dto.EmpDTO;
import com.emp1.model.Employee;
import com.emp1.service.EmpService;

@RestController
@RequestMapping("/home")
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	public EmpController(com.emp1.service.EmpService empService) {
		empService = empService;
	}


	@GetMapping("/emp")
	public List<EmpDTO> getEmp() {
		
		return empService.show();
		}
	
	@PostMapping(value = "/emp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addEmployees(
	        @RequestPart("name") String name,@RequestPart("address") String address,
	        @RequestPart("deptname") String deptname,
	        @RequestPart("photo") MultipartFile photo) throws IOException {

	    //System.out.println("File content type: " + photo.getContentType());
	    System.out.println("controller is running");
	    //System.out.println(empDto);
	    empService.createUser(name,address,deptname, photo);
	    // You can add your logic here to handle empDto and photo

	    return ResponseEntity.ok("Employee created successfully");
	}

	
	@PutMapping(value = "/emp/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Employee updateEmployees( @PathVariable int id,@RequestPart("name") String name,@RequestPart("address") String address,
	        @RequestPart("deptname") String deptname,
	        @RequestPart("photo") MultipartFile photo) throws IOException {
		
		return empService.updateEmp(id,name,address,deptname, photo);
	}
	
	@DeleteMapping("/emp/{id}")
	public String deleteEmployee(@PathVariable int id) {
		System.out.println(id);
		return empService.deleteEmp(id);
	}
	
	
	

}
