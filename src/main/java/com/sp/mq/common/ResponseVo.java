package com.sp.mq.common;


import java.io.Serializable;

/**
 *
 * restful统一返回对象
 * Created by alexlu on 2017/9/12.
 */
public class ResponseVo implements Serializable{
    /**
     * 成功标识
     */
    private boolean success;


    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private int code;


    /**
     * 返回对象
     */
    private Object data;

//    /**
//     * 错误级别
//     */
//    private int errorLevel;

    public ResponseVo(){}


    public ResponseVo(int code, String message){
        this.message = message;
        this.code = code;
        if(code>=20000 && code < 30001){
            this.success = true;
        }
    };

    public ResponseVo(boolean success, String message, int code){
        this.message = message;
        this.code = code;
        this.success = success;
    };

    public ResponseVo(String message, int code, Object data){
        this(code,message);
        this.data = data;
    };

    public ResponseVo(boolean success, String message, int code, Object data){
        this(success,message,code);
        this.data = data;
    };
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseVo{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
