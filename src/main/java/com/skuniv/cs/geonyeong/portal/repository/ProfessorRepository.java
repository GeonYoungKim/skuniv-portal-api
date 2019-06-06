package com.skuniv.cs.geonyeong.portal.repository;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    public Optional<Professor> findByIdAndPassword(String id, String password);

}
