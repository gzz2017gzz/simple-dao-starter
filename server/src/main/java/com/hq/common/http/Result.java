package com.hq.common.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Result {
    private Integer code;
    private String message;
    private Data result;

    @Setter
    @Getter
    @ToString
    public static class Data {
        private String name;
        private String idcard;
        private int res;
        private String description;
        private String sex;
        private String address;
    }

}
