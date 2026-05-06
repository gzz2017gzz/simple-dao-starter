package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【岗位枚举】
 * @date 2024-10-09 13:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum PostType {
    FINANCE("18", "财务"),
    TEACH_MASTER("19", "班主任"),
    COUNSELOR("11", "课程顾问"),
    COUNSELOR_LEADER("12", "课程顾问主管"),
    DORM_MANAGE("14", "宿管主管"),
    STUDENT_MANAGE("15", "学管"),
    STUDENT_MANAGE_LEADER("16", "学管主管"),
    CLASS_TEACHER("20", "教学老师"),
    TEACH_MANAGE("26", "教学主管"),
    ROOM_MANAGE("34", "宿管"),
    REPAIRER("35", "维修"),
    CHANNEL_LEADER("37", "渠道主管"),
    COUNSELOR_MANAGER("38", "课程顾问大主管"),
    DATA_ATTACHE("39", "数据专员"),
    ;
    private final String code;
    private final String name;

    public static boolean isStudentManage(Byte code) {
        return Byte.valueOf(STUDENT_MANAGE.getCode()).equals(code);
    }

    public static boolean isStudentManageLeader(Byte code) {
        return Byte.valueOf(STUDENT_MANAGE_LEADER.getCode()).equals(code);
    }

    public static boolean isTeachManage(Byte code) {
        return Byte.valueOf(TEACH_MANAGE.getCode()).equals(code);
    }

    public static boolean isCs(Byte code) {
        return Byte.valueOf(COUNSELOR.getCode()).equals(code);
    }

    public static boolean isDormManage(Byte code) {
        return Byte.valueOf(DORM_MANAGE.getCode()).equals(code);
    }

    public static boolean isTeachMaster(Byte code) {
        return Byte.valueOf(TEACH_MASTER.getCode()).equals(code);
    }

    public static boolean isClassTeacher(Byte code) {
        return Byte.valueOf(CLASS_TEACHER.getCode()).equals(code);
    }

    public static boolean isChannelLeader(Byte code) {
        return Byte.valueOf(CHANNEL_LEADER.getCode()).equals(code);
    }

    public static boolean isCounselorManager(Byte code) {
        return Byte.valueOf(COUNSELOR_MANAGER.getCode()).equals(code);
    }

    public static boolean isCounselorLeader(Byte code) {
        return Byte.valueOf(COUNSELOR_LEADER.getCode()).equals(code);
    }

    public static boolean isDataAttache(Byte code) {
        return Byte.valueOf(DATA_ATTACHE.getCode()).equals(code);
    }
}
