package com.records.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.records.Bean.UserDetails;
import com.records.Repository.UserJdbcRepository;

@Service
public class UserServiceImplementation implements UserServiceInterface {


	@Autowired
	UserJdbcRepository userJdbcRepository;
	
//	public UserDetails createUser(UserDetails user) {
//		
//		 try {
//	            byte[] photoBytes = null;
//	            
//	           
//	            if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
//	            	photoBytes = user.getPhoto().getBytes(); 
//	            }
//
//	            //ethe issue hota sarv data null kafrt ahe tu new object ne 
//	           
////	            UserDetails newUser = new UserDetails();
//	            user.setPhotoBytes(photoBytes);
//	           
//	            userJdbcRepository.insert(user);
//
//	            return user;
//	        }
//		 		catch (IOException e) {
//	            e.printStackTrace();
//	            return null; 
//	        }
//	    }
//
	
	
	
	public UserDetails createUser(UserDetails user) {
        if (user.getPhotoBytes() == null || user.getPhotoBytes().length == 0) {
            System.out.println("Error: Photo bytes are missing!");
            return null; 
        }

        // Save user in MySQL database
        userJdbcRepository.insert(user);

        return user;
    }

    
	public UserDetails getUserById(int id) {
		
		return userJdbcRepository.getUserById(id);
	}
	
}
