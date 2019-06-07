package com.skuniv.cs.geonyeong.portal.constant;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class PortalConstant {
    public static final String PROFESSOR_ID_KEY = "professorId";
    public static final String STUDENT_ID_KEY = "studentId";

    public static final BiMap<String, Integer> DAY_OF_WEEK_MAP = HashBiMap.create();
    static {
        DAY_OF_WEEK_MAP.put("일", 1);
        DAY_OF_WEEK_MAP.put("월", 2);
        DAY_OF_WEEK_MAP.put("화", 3);
        DAY_OF_WEEK_MAP.put("수", 4);
        DAY_OF_WEEK_MAP.put("목", 5);
        DAY_OF_WEEK_MAP.put("금", 6);
        DAY_OF_WEEK_MAP.put("토", 7);
    }
}
