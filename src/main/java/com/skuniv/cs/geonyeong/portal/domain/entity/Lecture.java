package com.skuniv.cs.geonyeong.portal.domain.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
    name = "lecture"
)
@Data
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double lectureTime;
    private Double score;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "student_lecture",
        joinColumns = @JoinColumn(name = "lecture_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> studentList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<LectureDetail> lectureDetailList;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Assignment> assignmentList;

}
