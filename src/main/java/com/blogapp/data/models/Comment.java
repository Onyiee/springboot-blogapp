package com.blogapp.data.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Comment {
    @Id
    private Integer id;

    private String commentatorName;

    @Column(nullable = false, length = 200)
    private String content;

    private LocalDate dateCreated;
}
