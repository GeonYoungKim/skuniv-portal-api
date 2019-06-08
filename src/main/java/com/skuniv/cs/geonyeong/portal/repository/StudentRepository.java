package com.skuniv.cs.geonyeong.portal.repository;

import com.skuniv.cs.geonyeong.portal.domain.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

    public Optional<Student> findByIdAndPassword(String id, String password);

}
