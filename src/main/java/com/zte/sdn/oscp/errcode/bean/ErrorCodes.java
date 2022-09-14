package com.zte.sdn.oscp.errcode.bean;

import java.util.ArrayList;
import java.util.List;

public class ErrorCodes {
    public List<ErrorCode> errcodes = new ArrayList<>();

    //get set
    public static class ErrorCode {
        private String code;
        private String level;
        private String label;
        //get set

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}