package com.skuniv.cs.geonyeong.portal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import lombok.Builder;
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
    @Column(name = "lecture_day")
    private String lectureDay;
    @Column(name = "lecture_time")
    private Double lectureTime;
    private Double score;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_lecture",
        joinColumns = @JoinColumn(name = "lecture_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonIgnore
    private List<Student> studentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "semester_id")
    @JsonIgnore
    private Semester semester;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LectureDetail> lectureDetailList = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assignment> assignmentList = new ArrayList<>();

    @Builder
    public Lecture(String name, String lectureDay, Double lectureTime, Double score,
        List<Student> studentList, Professor professor,
        Semester semester,
        List<LectureDetail> lectureDetailList,
        List<Assignment> assignmentList) {
        this.name = name;
        this.lectureDay = lectureDay;
        this.lectureTime = lectureTime;
        this.score = score;
        this.studentList = studentList;
        this.professor = professor;
        this.semester = semester;
        this.lectureDetailList = lectureDetailList;
        this.assignmentList = assignmentList;
    }

    public void addLectureDetail(LectureDetail lectureDetail) {
        lectureDetail.setLecture(this);
        this.lectureDetailList.add(lectureDetail);
    }

    public void addAssignment(Assignment assignment) {
        assignment.setLecture(this);
        this.assignmentList.add(assignment);
    }
}
