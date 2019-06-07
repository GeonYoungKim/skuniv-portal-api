package com.skuniv.cs.geonyeong.portal.repository;

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.entity.Semester;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findBySemesterAndProfessor(Semester semester, Professor professor);

}
