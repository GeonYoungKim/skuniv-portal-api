package com.skuniv.cs.geonyeong.portal.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentAssignment is a Querydsl query type for StudentAssignment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudentAssignment extends EntityPathBase<StudentAssignment> {

    private static final long serialVersionUID = 1046374575L;

    public static final QStudentAssignment studentAssignment = new QStudentAssignment("studentAssignment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath part = createString("part");

    public QStudentAssignment(String variable) {
        super(StudentAssignment.class, forVariable(variable));
    }

    public QStudentAssignment(Path<? extends StudentAssignment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentAssignment(PathMetadata metadata) {
        super(StudentAssignment.class, metadata);
    }

}

