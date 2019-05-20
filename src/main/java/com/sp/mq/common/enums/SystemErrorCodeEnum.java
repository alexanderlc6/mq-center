package com.sp.mq.common.enums;

/**
 * 2开头是操作成功代码
 * 3开头是认证失败代码 30100以内是保留数字错误码，用户误自定义
 * 4开头是系统相关错误 40100以内是保留数字错误码，用户误自定义
 */
public enum SystemErrorCodeEnum {
    SUCCESS(20000, "操作成功!"),
    UN_PERMISSION(30005, "无权访问!"),
    SYSTEM_ERROR(40000, "系统错误!"),
    INTERFACE_ERROR(40001,"接口调用错误!"),
    DATABASE_ERROR(40002,"数据库连接错误!"),
    IO_ERROR(40003,"IO错误!"),
    ILLEGAL_PARAMETER(40004,"参数错误!"),
    DATEBASE_ERROR(40005,"数据入库失败!"),
    API_ERROR(40006,"api调用失败!"),
    EXCEPTION(40007,"系统异常!");

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;


    /**
     * 使用错误码和错误信息构造枚举
     *
     * @param code    错误码
     * @param message 错误信息
     */
    SystemErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message != null ? message : "";
    }

    /**
     * 获取错误码
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
