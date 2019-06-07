package com.skuniv.cs.geonyeong.portal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(
    name = "student",
    indexes = {
        @Index(name = "IPK_PHONE", columnList = "phone", unique = true),
        @Index(name = "IPK_EMAIL", columnList = "email", unique = true)
    }
)
@NoArgsConstructor
public class Student {

    @Id
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_assignment",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    @JsonIgnore
    private List<Assignment> assignmentList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_lecture",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    @JsonIgnore
    private List<Lecture> lectureList = new ArrayList<>();
}
