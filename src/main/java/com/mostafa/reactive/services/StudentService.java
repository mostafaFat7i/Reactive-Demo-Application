package com.mostafa.reactive.services;

import com.mostafa.reactive.StudentCreatedEvent;
import com.mostafa.reactive.StudentRequest;
import com.mostafa.reactive.controllers.StudentController;
import com.mostafa.reactive.entities.Student;
import com.mostafa.reactive.repos.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepository;


    private final ApplicationEventPublisher eventPublisher;


    public Flux<Student> findAll() {
        return studentRepository.findAll();
    }

    public Mono<Student> save(StudentRequest request) {
        return studentRepository.save(
                Student.builder()
                        .name(request.getName())
                        .age(request.getAge())
                        .build()
        ).doOnSuccess(student -> eventPublisher.publishEvent(new StudentCreatedEvent(this, student)));
    }


}
