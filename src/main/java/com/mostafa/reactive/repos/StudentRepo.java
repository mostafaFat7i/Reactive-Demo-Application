package com.mostafa.reactive.repos;

import com.mostafa.reactive.entities.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StudentRepo extends ReactiveCrudRepository<Student, Integer> {
}
