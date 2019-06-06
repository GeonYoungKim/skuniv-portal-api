package com.skuniv.cs.geonyeong.portal.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -428049531L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLecture lecture = new QLecture("lecture");

    public final ListPath<Assignment, QAssignment> assignmentList = this.<Assignment, QAssignment>createList("assignmentList", Assignment.class, QAssignment.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LectureDetail, QLectureDetail> lectureDetailList = this.<LectureDetail, QLectureDetail>createList("lectureDetailList", LectureDetail.class, QLectureDetail.class, PathInits.DIRECT2);

    public final NumberPath<Double> lectureTime = createNumber("lectureTime", Double.class);

    public final StringPath name = createString("name");

    public final QProfessor professor;

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final ListPath<Student, QStudent> studentList = this.<Student, QStudent>createList("studentList", Student.class, QStudent.class, PathInits.DIRECT2);

    public QLecture(String variable) {
        this(Lecture.class, forVariable(variable), INITS);
    }

    public QLecture(Path<? extends Lecture> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLecture(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLecture(PathMetadata metadata, PathInits inits) {
        this(Lecture.class, metadata, inits);
    }

    public QLecture(Class<? extends Lecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.professor = inits.isInitialized("professor") ? new QProfessor(forProperty("professor")) : null;
    }

}

