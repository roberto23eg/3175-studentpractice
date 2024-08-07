package com.example.studentpractice;

import com.example.studentpractice.entities.Student;
import com.example.studentpractice.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class StudentpracticeApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudentpracticeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {

		return args -> {
			studentRepository.save(new Student(null, "Alice", new Date(), true, 3.5));
			studentRepository.save(new Student(null, "Bob", new Date(), false, 2.5));
			studentRepository.save(new Student(null, "Charlie", new Date(), true, 3.0));
			studentRepository.save(new Student(null,"David", new Date(), false, 2.0));

			studentRepository.findAll().forEach(student -> {
				System.out.println(student.getName());
			});
		};
	}

}
