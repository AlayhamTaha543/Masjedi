package com.mosque.masjedi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.NaturalId;
import com.mosque.masjedi.entity.enums.UserRole;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = @Index(columnList = "username"))
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "circle", "mosque", "taughtCircles" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class User extends BaseEntity {

    @NaturalId
    @NotBlank
    @Column(unique = true, updatable = false)
    private String username;

    @Convert(converter = PasswordConverter.class)
    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_circle_id")
    private Circle circle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_mosque_id")
    private Mosque mosque;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Circle> taughtCircles = new ArrayList<>();

    // Helper methods
    public void joinCircle(Circle circle) {
        this.circle = circle;
        circle.getStudents().add(this);
    }

    public void leaveCircle() {
        if (this.circle != null) {
            this.circle.getStudents().remove(this);
            this.circle = null;
        }
    }
}