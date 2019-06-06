package com.skuniv.cs.geonyeong.portal.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureDetail is a Querydsl query type for LectureDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLectureDetail extends EntityPathBase<LectureDetail> {

    private static final long serialVersionUID = -1386215370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureDetail lectureDetail = new QLectureDetail("lectureDetail");

    public final BooleanPath canceled = createBoolean("canceled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLecture lecture;

    public final DateTimePath<java.util.Date> lectureDate = createDateTime("lectureDate", java.util.Date.class);

    public final StringPath lectureDay = createString("lectureDay");

    public final NumberPath<Double> lectureDetailTime = createNumber("lectureDetailTime", Double.class);

    public QLectureDetail(String variable) {
        this(LectureDetail.class, forVariable(variable), INITS);
    }

    public QLectureDetail(Path<? extends LectureDetail> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLectureDetail(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLectureDetail(PathMetadata metadata, PathInits inits) {
        this(LectureDetail.class, metadata, inits);
    }

    public QLectureDetail(Class<? extends LectureDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

