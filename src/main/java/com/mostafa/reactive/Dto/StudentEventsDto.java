package com.mostafa.reactive.Dto;

import com.mostafa.reactive.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentEventsDto {
    private List<Student> students;
}
