package com.example.spring_mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, Student> {
    Optional<Student> findStudentByEmail(String email);

//    @Query(" ")
//    void test();

}
