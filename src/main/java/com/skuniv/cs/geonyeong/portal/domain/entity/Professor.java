package com.skuniv.cs.geonyeong.portal.domain.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "professor",
    indexes = {
        @Index(name = "IPK_PHONE", columnList = "phone"),
        @Index(name = "IPK_EMAIL", columnList = "email")
    }
)
@Data
@NoArgsConstructor
public class Professor {
    @Id
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Lecture> lectureList;

}
