package com.mosque.masjedi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @Column(name = "`order`")
    private int order;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
