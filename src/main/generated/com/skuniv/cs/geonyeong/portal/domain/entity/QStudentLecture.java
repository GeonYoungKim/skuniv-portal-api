package com.skuniv.cs.geonyeong.portal.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentLecture is a Querydsl query type for StudentLecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudentLecture extends EntityPathBase<StudentLecture> {

    private static final long serialVersionUID = -2006628644L;

    public static final QStudentLecture studentLecture = new QStudentLecture("studentLecture");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QStudentLecture(String variable) {
        super(StudentLecture.class, forVariable(variable));
    }

    public QStudentLecture(Path<? extends StudentLecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentLecture(PathMetadata metadata) {
        super(StudentLecture.class, metadata);
    }

}

