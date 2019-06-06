package com.skuniv.cs.geonyeong.portal.service;

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.repository.LectureRepository;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;

    public List<Lecture> getLectures(String professorId) {
        Professor professor = professorRepository.findById(professorId).get();
        return lectureRepository.findByProfessor(professor);
    }

    public Lecture createLecture(String professorId, Lecture lecture) {
        Professor professor = professorRepository.findById(professorId).get();
        professor.addLecture(lecture);
        professorRepository.save(professor);
        return lecture;
    }
}
