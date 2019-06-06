package com.skuniv.cs.geonyeong.portal.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_lecture")
@Data
@NoArgsConstructor
public class StudentLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
