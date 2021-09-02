package com.example.jpa;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.jpa.model.Gender;
import com.example.jpa.model.User;
import com.example.jpa.model.UserProfile;
import com.example.jpa.repository.UserProfileRepository;
import com.example.jpa.repository.UserRepository;

@SpringBootApplication
public class JpaOneToOneDemoApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private UserProfileRepository userProfileRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaOneToOneDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Clean up database tables
		userProfileRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

		//=========================================

		// Create a User instance
		User user = new User("Jithin", "baby", "jithin.m.baby@gmail.com",
				"MY_SUPER_SECRET_PASSWORD");
		System.out.println("User >>>>>");
		System.out.println(user);

		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set(1992, 7, 21);

		// Create a UserProfile instance
		UserProfile userProfile = new UserProfile("+91-9745892625", Gender.MALE, dateOfBirth.getTime(),
				"747", "2nd Cross", "Golf View Road, Kodihall", "Bangalore",
				"Karnataka", "India", "560008");

		System.out.println(userProfile);
		// Set child reference(userProfile) in parent entity(user)
		user.setUserProfile(userProfile);

		// Set parent reference(user) in child entity(userProfile)
		userProfile.setUser(user);

		// Save Parent Reference (which will save the child as well)
		userRepository.save(user);

		//=========================================
	}
}

//ref : https://github.com/callicoder/jpa-hibernate-tutorials
//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-one-mapping-example/
