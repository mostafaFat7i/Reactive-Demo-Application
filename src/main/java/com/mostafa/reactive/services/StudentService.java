package com.mostafa.reactive.services;

import com.mostafa.reactive.StudentEvents;
import com.mostafa.reactive.Dto.StudentRequest;
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
        ).doOnSuccess(student -> eventPublisher.publishEvent(new StudentEvents(this, student, StudentEvents.EventType.CREATED)));
    }

    public Mono<Student> update(Integer id, StudentRequest request) {
        return studentRepository.findById(id)
                .flatMap(student -> {
                    student.setName(request.getName());
                    student.setAge(request.getAge());
                    return studentRepository.save(student);
                })
                .doOnSuccess(student -> eventPublisher.publishEvent(new StudentEvents(this, student, StudentEvents.EventType.UPDATED)));
    }

    public Mono<Void> delete(Integer id) {
        return studentRepository.findById(id)
                .flatMap(student -> studentRepository.delete(student)
                        .doOnSuccess(unused -> eventPublisher.publishEvent(new StudentEvents(this, student, StudentEvents.EventType.DELETED))));
    }

    public Mono<Void> deleteAll() {
        return studentRepository.deleteAll()
                .doOnSuccess(unused -> eventPublisher.publishEvent(new StudentEvents(this, null, StudentEvents.EventType.DELETED_ALL)));
    }

}
