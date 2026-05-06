package com.hq.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum SMSTpl {

    VERIFY_CODE("SMS_475295479", "华卿教育", "code", "验证码"),
    EXPERIENCE_NOTICE("SMS_463535072", "华卿教育", "name,address", "体验课通知"),
    PRE_SIGNUP_NOTICE("SMS_463642765", "华卿教育", "name,price,address", "预报名通知"),
    ENTER_SCHOOL_BILL_CODE("SMS_463648214", "华卿教育", "name,price,address", "入学收据通知"),
    RENEW_BILL_CODE("SMS_463765577", "华卿教育", "name,price,address", "续费收据通知"),

    //请假
    VACATION_AUDIT("SMS_483485296", "华卿教育", "name,start,end,result", "学生请假审核通知"),
    VACATION_LEAVE_RETURN("SMS_483340442", "华卿教育", "name,time,result", "学生请假离校返校通知"),
    VACATION_NOT_RETURN("SMS_483310423", "华卿教育", "name,start,end", "学生请假结束未返校"),
    ;

    /**
     * 短信模板编码
     */
    final private String code;

    /**
     * 签名
     */
    final private String signName;

    /**
     * 短信模板必需的数据名称，多个key以逗号分隔，此处配置作为校验
     */
    final private String params;

    /**
     * 短信说明
     */
    final private String desc;
}

