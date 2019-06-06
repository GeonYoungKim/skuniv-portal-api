package com.skuniv.cs.geonyeong.portal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "professor",
    indexes = {
        @Index(name = "IPK_PHONE", columnList = "phone"),
        @Index(name = "IPK_EMAIL", columnList = "email")
    }
)
@Data
@NoArgsConstructor
public class Professor {

    @Id
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lecture> lectureList;

    @Builder
    public Professor(String id, String password, String name, String phone, String email,
        List<Lecture> lectureList) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.lectureList = lectureList;
    }

    public void addLecture(Lecture lecture) {
        lecture.setProfessor(this);
        this.lectureList.add(lecture);
    }
}
