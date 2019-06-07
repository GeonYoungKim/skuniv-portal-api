package com.skuniv.cs.geonyeong.portal.repository;

import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import java.util.List;

public interface StudentAssignmentRepositoryCustom {
    public List<ProfessorAssignmentDetail> findByAssignmentId(Long assignmentId);
}
