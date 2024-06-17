package com.mostafa.reactive.controllers;

import com.mostafa.reactive.StudentCreatedEvent;
import com.mostafa.reactive.StudentRequest;
import com.mostafa.reactive.entities.Student;
import com.mostafa.reactive.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    private final Sinks.Many<Student> studentSink;


    public StudentController(StudentService studentService) {
        this.studentSink = Sinks.many().multicast().onBackpressureBuffer();
    }


    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> findAll() {
        return studentSink.asFlux().mergeWith(studentService.findAll());
    }

    @EventListener
    public void onStudentCreated(StudentCreatedEvent event) {
        studentSink.tryEmitNext(event.getStudent());
    }


    @PostMapping("/save")
    public Mono<Student> save(@RequestBody StudentRequest student) {
        return studentService.save(student);
    }

}
