package com.skuniv.cs.geonyeong.portal.repository;


import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureDetailRepository extends JpaRepository<LectureDetail, Long> {
    List<LectureDetail> findByLecture(Lecture lecture);
}
