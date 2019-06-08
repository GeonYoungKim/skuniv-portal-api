package com.skuniv.cs.geonyeong.portal.repository;

import static com.skuniv.cs.geonyeong.portal.domain.entity.QStudent.student;
import static com.skuniv.cs.geonyeong.portal.domain.entity.QStudentAssignment.studentAssignment;

import com.querydsl.core.types.Projections;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentAssignmentRepositoryImpl implements StudentAssignmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProfessorAssignmentDetail> findByAssignmentId(Long assignmentId) {
        return queryFactory
            .select(Projections.fields(ProfessorAssignmentDetail.class,
                student.name.as("name"),
                studentAssignment.part.as("part"),
                student.phone.as("phone")
            )).from(studentAssignment)
            .where(studentAssignment.assignmentId.eq(assignmentId))
            .join(student).on(studentAssignment.studentId.eq(student.id))
            .fetch()
            ;
    }
}
