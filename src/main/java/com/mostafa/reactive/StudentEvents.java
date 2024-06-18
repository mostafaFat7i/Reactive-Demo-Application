package com.mostafa.reactive;

import com.mostafa.reactive.entities.Student;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StudentEvents extends ApplicationEvent {
    public enum EventType {
        CREATED, UPDATED, DELETED, DELETED_ALL
    }

    private final Student student;
    private final EventType eventType;

    public StudentEvents(Object source, Student student, EventType eventType) {
        super(source);
        this.student = student;
        this.eventType = eventType;
    }



}

