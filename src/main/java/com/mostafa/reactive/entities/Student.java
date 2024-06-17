package com.mostafa.reactive.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table("students")
public class Student {

    @Id
    private Integer id;
    private String name;
    private int age;
}
