package com.wjl.xczx.content.course.consts;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public class CourseConstant {
    public static enum ChargeModel {
        FREE("201000", "免费"),
        TOLL("201001", "收费");

        ChargeModel(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static final int ID = 16;
        public static final String NAME = "课程收费情况";
        public static final String CODE = "201";
        private String code;
        private String message;


        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static enum Grade {
        JUNIOR("204001", "初级"),
        INTERMEDIATE("204002", "中级"),
        SENIOR("204003", "高级");
        public static final int ID = 17;
        public static final String NAME = "课程等级";
        public static final String CODE = "204";

        Grade(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private String code;
        private String message;


        public String getCode() {
            return code;
        }


        public String getMessage() {
            return message;
        }
    }

    public static enum ModelStatus {
        RECORD("200002", "录播"),
        LIVE("200003", "直播");

        ModelStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static final int ID = 18;
        public static final String NAME = "课程模式状态";
        public static final String CODE = "200";
        private String code;
        private String message;

        public String getCode() {
            return code;
        }


        public String getMessage() {
            return message;
        }
    }

    public static enum PublishStatus {
        NOT("203001", "未发布"),
        OK("203002", "已发布"),
        DEED("203003","下线");

        PublishStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static final int ID = 19;
        public static final String NAME = "课程发布状态";
        public static final String CODE = "203";
        private String code;
        private String message;

        public String getCode() {
            return code;
        }


        public String getMessage() {
            return message;
        }
    }


    public static enum AuditStatus {
        FAILED("202001", "审核未通过"),
        NOT_COMMIT("202002", "未提交"),
        COMMIT("202003","已提交"),
        OK("202004","通过");

        AuditStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static final int ID = 15;
        public static final String NAME = "课程审核状态";
        public static final String CODE = "202";
        private String code;
        private String message;

        public String getCode() {
            return code;
        }


        public String getMessage() {
            return message;
        }
    }
}
