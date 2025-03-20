package com.mosque.masjedi.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "circles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Circle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mosque_id")
    private Mosque mosque;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "circle", fetch = FetchType.LAZY)
    private Set<User> students = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Helper methods
    public void addStudent(User student) {
        students.add(student);
        student.setCircle(this);
    }

    public void removeStudent(User student) {
        students.remove(student);
        student.setCircle(null);
    }
}