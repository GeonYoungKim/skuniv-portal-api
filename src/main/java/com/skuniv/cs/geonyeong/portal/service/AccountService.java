package com.skuniv.cs.geonyeong.portal.service;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.entity.Student;
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse;
import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.exception.SignInException;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import com.skuniv.cs.geonyeong.portal.repository.StudentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final CryptorService cryptorService;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;

    // front에서 email, phone의 유효성 검사가 완료되어서 넘어왔다고 가정.
    public Professor signUp(Professor professor) {
        String password = professor.getPassword();
        professor.setPassword(cryptorService.encryptBase64(password));
        Professor signUpProfessor = professorRepository.save(professor);
        return signUpProfessor;
    }

    public AccountResponse signIn(Professor professor) {
        String password = cryptorService.encryptBase64(professor.getPassword());
        Optional<Professor> findProfessorOptional = professorRepository
            .findByIdAndPassword(professor.getId(), password);
        if (findProfessorOptional.isPresent()) {
            Professor findProfessor = findProfessorOptional.get();
            String token = jwtService.makeJwt(findProfessor.getId(), findProfessor.getPassword());
            return AccountResponse.builder().token(token).name(findProfessor.getName()).accountType(
                AccountType.PROFESSOR).build();
        }
        throw new SignInException();
    }

    public AccountResponse signIn(Student student) {
        String password = cryptorService.encryptBase64(student.getPassword());
        Optional<Student> findStudentOptional = studentRepository
            .findByIdAndPassword(student.getId(), password);
        if (findStudentOptional.isPresent()) {
            Student findStudent = findStudentOptional.get();
            String token = jwtService.makeJwt(findStudent.getId(), findStudent.getPassword());
            return AccountResponse.builder().token(token).name(findStudent.getId())
                .accountType(AccountType.STUDENT).build();
        }
        throw new SignInException();
    }

    public Student signUp(Student student) {
        String password = student.getPassword();
        student.setPassword(cryptorService.encryptBase64(password));
        Student signUpStudent = studentRepository.save(student);
        return signUpStudent;
    }
}
