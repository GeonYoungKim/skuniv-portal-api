package com.skuniv.cs.geonyeong.portal.service;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.exception.ProfessorSignInException;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import java.io.UnsupportedEncodingException;
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
    private final JwtService jwtService;

    // front에서 email, phone의 유효성 검사가 완료되어서 넘어왔다고 가정.
    public Professor signUp(Professor professor) {
        String password = professor.getPassword();
        professor.setPassword(cryptorService.encryptBase64(password));
        Professor signUpProfessor = professorRepository.save(professor);
        return signUpProfessor;
    }

    public String signIn(Professor professor) {
        String password = cryptorService.encryptBase64(professor.getPassword());
        Optional<Professor> findProfessorOptional = professorRepository.findByIdAndPassword(professor.getId(), password);
        if(findProfessorOptional.isPresent()) {
            Professor findProfessor = findProfessorOptional.get();
            return jwtService.makeJwt(findProfessor.getId(), findProfessor.getPassword());
        }
        throw new ProfessorSignInException();
    }
}
