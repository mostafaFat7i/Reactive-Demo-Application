package com.mostafa.reactive.controllers;

import com.mostafa.reactive.StudentEvents;
import com.mostafa.reactive.Dto.StudentRequest;
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

    private final Sinks.Many<StudentEvents> studentSink;


    public StudentController(StudentService studentService) {
        this.studentSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StudentEvents> findAll() {
        return studentSink.asFlux().mergeWith(studentService.findAll().map(student -> new StudentEvents(this, student, StudentEvents.EventType.CREATED)));
    }

    @PostMapping("/save")
    public Mono<Student> save(@RequestBody StudentRequest student) {
        return studentService.save(student);
    }

    @PutMapping("/update/{id}")
    public Mono<Student> update(@PathVariable Integer id, @RequestBody StudentRequest request) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return studentService.delete(id);
    }
    @DeleteMapping("/reset")
    public Mono<Void> deleteAll() {
        return studentService.deleteAll();
    }

    @EventListener
    public void onStudentListChanges(StudentEvents event) {
        studentSink.tryEmitNext(event);
    }

}
