package com.skuniv.cs.geonyeong.portal.service;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import com.skuniv.cs.geonyeong.portal.repository.StudentRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${com.skuniv.cs.geonyeong.jwt.secretKey}")
    private String secretKey;

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    private final Long EXPIRE_TIME = 1000 * 60 * 60 * 1l;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final String ID_FIELD = "id";
    private final String PASSWORD_FIELD = "password";
    private final Map<String, Object> headerMap = new HashMap<String, Object>() {{
        put("typ", "JWT");
        put("alg", "HS256");
    }};

    public String makeJwt(String id, String password) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> map = new HashMap<String, Object>() {{
            put(ID_FIELD, id);
            put(PASSWORD_FIELD, password);
        }};

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + EXPIRE_TIME);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
            .setClaims(map)
            .setExpiration(expireTime)
            .signWith(signingKey, signatureAlgorithm);

        return builder.compact();
    }

    public boolean checkProcessor(String jwt) {
        try {
            Claims claims = makeClaims(jwt);
            String id = String.valueOf(claims.get(ID_FIELD));
            String password = String.valueOf(claims.get(PASSWORD_FIELD));
            Optional<Professor> professorOptional = professorRepository.findByIdAndPassword(id, password);
            if(professorOptional.isPresent())
                return true;
            return false;
        } catch (ExpiredJwtException exception) {
            throw exception;
        } catch (JwtException exception) {
            log.info("토큰 변조");
            return false;
        }
    }

    public boolean checkStudent(String jwt) {
        try {
            Claims claims = makeClaims(jwt);
            return true;
        } catch (ExpiredJwtException exception) {
            throw exception;
        } catch (JwtException exception) {
            log.info("토큰 변조");
            return false;
        }
    }

    private Claims makeClaims(String jwt) {
        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(jwt).getBody();
    }

    public String getJwtId(String jwt) {
        Claims claims = makeClaims(jwt);
        return (String) claims.get(ID_FIELD);
    }
}
