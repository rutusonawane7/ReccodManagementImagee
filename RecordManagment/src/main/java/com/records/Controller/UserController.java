package com.records.Controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.records.Bean.UserDetails;
import com.records.Service.UserServiceImplementation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

	@Autowired
	UserServiceImplementation service;
	
	//@PostMapping(value = "/addUser", consumes = "multipart/form-data")
//	@PostMapping(value = "/addUser", consumes = "multipart/form-data", produces = "application/json")
//	@ApiResponse(responseCode = "200",description = "save the data")
//	public ResponseEntity<UserDetails> createUser (@RequestParam String name,@RequestParam MultipartFile file){
//		
//		UserDetails user = new UserDetails();
//		user.setName(name);
//		
//		try {
//            user.setPhotoBytes(file.getBytes()); // Convert MultipartFile to byte[]
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//        UserDetails userDetails = service.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userDetails);
//    }
	
	
	@PostMapping(value = "/addUser", consumes = "multipart/form-data", produces = "application/json")
	@ApiResponse(responseCode = "200",description = "save the data")
	public ResponseEntity<UserDetails> createUser(
	        @RequestPart("name") String name,
	        @RequestPart("file") MultipartFile file) {
		
		 System.out.println("Received Name: " + name);
		    System.out.println("Received File Name: " + file.getOriginalFilename());
		    System.out.println("File Size: " + file.getSize());

	    if (name == null || name.trim().isEmpty() || file.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    UserDetails user = new UserDetails();
	    user.setName(name);

	    try {
	    	byte[] photoBytes = file.getBytes();
	        user.setPhotoBytes(photoBytes); // Convert MultipartFile to byte[]
	        
	        System.out.println("Byte Array Length Before Saving: " + (photoBytes != null ? photoBytes.length : "NULL"));

	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }

	    UserDetails userDetails = service.createUser(user);
	    return ResponseEntity.status(HttpStatus.CREATED).body(userDetails);
	}
		

	
//	@PostMapping(value="/save")
//	@ApiResponse(responseCode = "200",description = "save the data")
//	public Boolean save(@RequestParam MultipartFile file) {
//		 UserDetails user= new  UserDetails();
//		 user.setName("ok");
//		 
//		 user.setPhoto(file);
//		 user.setId(12);
//		UserDetails userDetails = service.createUser(user);
//		if(userDetails!=null)
//			return true;
//		else
//			return false;
//	}
	
	@GetMapping("user/{id}")
	@ApiResponse(responseCode = "200",description = "save the data")
	public ResponseEntity<byte[]> getUserById(@PathVariable int id){
		UserDetails user = service.getUserById(id);

		
		if(user == null || user.getPhotoBytes()==null) {
			return ResponseEntity.notFound().build();
		}
		
			byte[] file=user.getPhotoBytes();
			
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);

	        
				return new ResponseEntity<>(file,headers, HttpStatus.OK);
		
	}
}
