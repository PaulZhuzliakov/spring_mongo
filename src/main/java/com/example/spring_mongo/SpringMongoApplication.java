package com.example.spring_mongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(
            StudentRepository studentRepository, MongoTemplate mongoTemplate) {
        return args -> {
            String email = "blade@mail.com";
            Address address = new Address("Russia", "Moscow", "846254");
            Student student = new Student("Boris", "TheBlade", email, Gender.MALE,
                    address, List.of("computer science"), BigDecimal.TEN, LocalDateTime.now());

//            usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);

            studentRepository.findStudentByEmail(email)
                    .ifPresentOrElse(s -> {
                        System.out.println("student already exists");
                    }, () -> {
                        studentRepository.insert(student);
                    });
        };
    }

    private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            System.out.println(students);
            System.out.println(students.size());
            throw new  IllegalStateException("found many students with email " + email);
        }
        if (students.isEmpty()) {
            studentRepository.insert(student);
        } else {
            System.out.println("student already exists");
        }
    }

}
