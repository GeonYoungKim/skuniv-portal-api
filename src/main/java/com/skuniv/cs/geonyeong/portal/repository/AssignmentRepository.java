package com.skuniv.cs.geonyeong.portal.repository;

import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByLecture(Lecture lecture);

}
