package com.emp1.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emp1.dto.EmpDTO;
import com.emp1.model.Dept;
import com.emp1.model.Employee;
import com.emp1.repository.DeptRepository;
import com.emp1.repository.EmpRepository;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class EmpService {
	
	@Autowired
	private final EmpRepository emprepository;
	@Autowired
    private final DeptRepository deptrepository;

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public EmpService(EmpRepository emprepository,
                      DeptRepository deptrepository,
                      @Value("${aws.region}") String region,
                      @Value("${aws.credentials.access-key}") String accessKey,
                      @Value("${aws.credentials.secret-key}") String secretKey) {

        this.emprepository = emprepository;
        this.deptrepository = deptrepository;

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

	

	public List<EmpDTO> show() {
		 List<EmpDTO> empDetails=emprepository.findAll().stream()
				.map(m->new EmpDTO(
						m.getId(),m.getImageUrl(),m.getName(),m.getAddress(),m.getDept().getDeptname()
						)).toList();
		return empDetails;
		
	}
	
	
	
	 // Handles the full process of uploading a user's photo and saving the user details
    public Employee createUser(String name, String address, String deptname, MultipartFile photo) throws IOException {
        // Upload photo to AWS S3 and get its URL
        String photoUrl = uploadFile(photo);
        
     // Create employee object
	    Employee emp1 = new Employee();
	    emp1.setName(name);
	    emp1.setAddress(address);
	    emp1.setImageUrl(photoUrl);
	    // Check if department already exists
	    Dept dept = deptrepository.findByDeptname(deptname);

	    if (dept == null) {
	        dept = new Dept();
	        dept.setDeptname(deptname);
	    }

	    emp1.setDept(dept);

	    return emprepository.save(emp1);

        // Create a new User entity and populate it with form data and S3 photo URL
//        Employee user = new Employee();
//        user.setName(name);
//        user.setEmail(email);
//        user.setAddress(address);
//        user.setPhotoUrl(photoUrl);
//
//        // Save the user to the database and return the saved entity
//        return userRepository.save(user);
    }
    
    
    // Uploads a file to AWS S3 and returns the public URL of the uploaded file
   
	
	
//	public Employee addEmp(EmpDTO empdto) {
//
//	    // Create employee object
//	    Employee emp1 = new Employee();
//	    emp1.setName(empdto.getName());
//	    emp1.setAddress(empdto.getAddress());
//
//	    // Check if department already exists
//	    Dept dept = deptrepository.findByDeptname(empdto.getDeptname());
//
//	    if (dept == null) {
//	        dept = new Dept();
//	        dept.setDeptname(empdto.getDeptname());
//	    }
//
//	    emp1.setDept(dept);
//
//	    return emprepository.save(emp1);
//	}


	public Employee updateEmp(int id,String name, String address, String deptname, MultipartFile photo) throws IOException {
		Employee emp = emprepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
		String photoUrl = uploadFile(photo);
	    Dept dept = deptrepository.findByDeptname(deptname);
	    if (dept == null) {
	        dept = new Dept();
	        dept.setDeptname(deptname);
	    }
	    emp.setName(name);
	    emp.setAddress(address);
	    emp.setImageUrl(photoUrl);
	    emp.setDept(dept);
	    //uploadFile(photo);
	    return emprepository.save(emp);
	}


	public String deleteEmp(int id) {
		// TODO Auto-generated method stub
		Employee emp = emprepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
		emprepository.delete(emp);
		deleteFileFromS3(emp.getImageUrl());
		
		return "emp deleted";
	}
	
	 private String uploadFile(MultipartFile file) throws IOException {
	        // Generate a unique file key using UUID and original filename
	        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

	        // Build the S3 put object request with metadata
	        PutObjectRequest request = PutObjectRequest.builder()
	                .bucket(bucketName)
	                .key(key)
	                .contentType(file.getContentType())
	                .build();

	        // Perform the upload using the S3 client
	        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
	        System.out.println("file loeded to the bucket");
	        // Return the URL to access the uploaded file
	        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
	    }
	 
	 public void deleteFileFromS3(String fileUrl) {
		    try {
		        // Extract the key from the file URL
		        String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

		        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
		                .bucket(bucketName)
		                .key(fileKey)
		                .build();

		        s3Client.deleteObject(deleteRequest);
		        System.out.println("File deleted from S3: " + fileKey);
		    } catch (Exception e) {
		        System.err.println("Failed to delete file from S3: " + e.getMessage());
		    }
		}

	
	

}
